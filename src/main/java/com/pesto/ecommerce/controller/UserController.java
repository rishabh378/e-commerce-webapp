package com.pesto.ecommerce.controller;

import com.pesto.ecommerce.models.request.LoginRequest;
import com.pesto.ecommerce.models.response.UserInfoResponse;
import com.pesto.ecommerce.service.UserService;
import com.pesto.ecommerce.validators.LoginValidators;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/otp/login")
    public String loginWithOtp(@Validated(LoginValidators.OTP.class)
                                 @RequestBody LoginRequest loginRequest) {
        return userService.generateOtp(loginRequest);
    }

    @PostMapping(value = "/otp/validate")
    public UserInfoResponse validateOtp(@Validated(LoginValidators.OTP.class)
                                            @RequestBody LoginRequest loginRequest) {
        return  userService.validateOtp(loginRequest);
    }
}
