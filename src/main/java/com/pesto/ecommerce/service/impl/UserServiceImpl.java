package com.pesto.ecommerce.service.impl;

import com.pesto.ecommerce.auth.jwt.JwtTokenType;
import com.pesto.ecommerce.auth.jwt.JwtTokenUtil;
import com.pesto.ecommerce.entity.OtpInfo;
import com.pesto.ecommerce.entity.UserInfo;
import com.pesto.ecommerce.enums.ContactType;
import com.pesto.ecommerce.enums.OtpType;
import com.pesto.ecommerce.exception.ApplicationException;
import com.pesto.ecommerce.models.request.LoginRequest;
import com.pesto.ecommerce.models.response.UserInfoResponse;
import com.pesto.ecommerce.otp.OtpFactory;
import com.pesto.ecommerce.otp.OtpProcessor;
import com.pesto.ecommerce.repository.OtpInfoRepository;
import com.pesto.ecommerce.repository.UserInfoRepository;
import com.pesto.ecommerce.service.UserAuthService;
import com.pesto.ecommerce.service.UserService;
import com.pesto.ecommerce.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserInfoRepository userInfoRepository;
    private final OtpFactory otpFactory;
    private final OtpInfoRepository otpInfoRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserAuthService userAuthService;

    @Override
    public String generateOtp(LoginRequest loginWithOtpDto) {
        OtpProcessor otpProcessor = otpFactory.get(loginWithOtpDto.getContactType());
        if (Objects.isNull(otpProcessor)) {
            log.error("No processor found for contactType:{} processor", loginWithOtpDto.getContactType());
            throw new ApplicationException(HttpStatus.NOT_FOUND, "No processor found");
        }
        otpProcessor.validateContact(loginWithOtpDto.getUserContact());
        return generateOtp(loginWithOtpDto.getUserContact(), loginWithOtpDto.getContactType(), loginWithOtpDto.getOtpType());
    }

    private String generateOtp(String userContact, ContactType contactType, OtpType otpType) {
        Optional<OtpInfo> userOtpOptional = otpInfoRepository.findByUserContactAndContactTypeAndOtpTypeAndIsActiveTrue(userContact, contactType, otpType);
        // deactivating old otp for above userEmail
        if (userOtpOptional.isPresent()) {
            userOtpOptional.get().setIsActive(Boolean.FALSE);
            otpInfoRepository.save(userOtpOptional.get());
        }

        // generating otp for above userEmail
        String otp =  CommonUtils.generateUserOtp(4);
        OtpInfo userOtp = new OtpInfo();
        userOtp.setUserContact(userContact);
        userOtp.setContactType(contactType);
        userOtp.setOtpType(otpType);
        userOtp.setIsActive(Boolean.TRUE);
        userOtp.setOtp(otp);
        otpInfoRepository.save(userOtp);

        log.info("[generateOtp] otp generated successfully for user : {} otp: {}", userContact, otp);
        return otp;
    }

    @Override
    public UserInfoResponse validateOtp(LoginRequest loginWithOtpDto) {

        ContactType contactType = loginWithOtpDto.getContactType();
        String userContact = loginWithOtpDto.getUserContact();

        OtpProcessor otpProcessor = otpFactory.get(contactType);

        if (Objects.isNull(otpProcessor)) {
            log.error("No processor found for contactType:{} processor", contactType);
            throw new ApplicationException(HttpStatus.NOT_FOUND, "No processor found");
        }
        otpProcessor.validateContact(userContact);
        validateUserOtp(loginWithOtpDto);
        // create or update user info and create default workspace if not exists
        UserInfo loginUser = upsertUser(userContact);

        final UserDetails userDetails = userAuthService.loadUserByUsername(userContact);
        final String token = jwtTokenUtil.generateToken(userDetails, JwtTokenType.ACCESS_TOKEN);

        return UserInfoResponse.builder()
                .userId(loginUser.getUserId())
                .name(loginUser.getName())
                .email(loginUser.getEmail())
                .token(token)
                .build();
    }

    private void validateUserOtp(LoginRequest loginWithOtpDto) {
        log.info("[validateOtp] method invoked, userContact : {}", loginWithOtpDto.getUserContact());
        OtpInfo userOtp = otpInfoRepository.findByUserContactAndContactTypeAndOtpTypeAndIsActiveTrue(loginWithOtpDto.getUserContact(), loginWithOtpDto.getContactType(), loginWithOtpDto.getOtpType())
                .orElseThrow(()-> new ApplicationException(HttpStatus.NOT_FOUND, "Otp is not generated"));
        String otp = loginWithOtpDto.getOtp();
        if (ObjectUtils.isEmpty(otp) || !otp.equals(userOtp.getOtp())) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Incorrect OTP, please try again");
        }
        if (LocalDateTime.now().isBefore(userOtp.getCreatedAt())
                || ChronoUnit.SECONDS.between(userOtp.getCreatedAt(), LocalDateTime.now()) > 300) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "OTP Expired, Please Resend OTP");
        }
        userOtp.setIsActive(Boolean.FALSE);
        otpInfoRepository.save(userOtp);
    }

    private UserInfo upsertUser(String userContact) {
        UserInfo loginUser = userInfoRepository.findByEmail(userContact).orElse(null);
        if (Objects.isNull(loginUser)) {
            loginUser = new UserInfo();
            loginUser.setUserId(CommonUtils.generateUserId());
            loginUser.setEmail(userContact);
            loginUser = userInfoRepository.save(loginUser);
        }
        return loginUser;
    }
}
