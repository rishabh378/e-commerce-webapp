package com.pesto.ecommerce.repository;

import com.pesto.ecommerce.entity.OtpInfo;
import com.pesto.ecommerce.enums.ContactType;
import com.pesto.ecommerce.enums.OtpType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpInfoRepository extends JpaRepository<OtpInfo, Long> {

    Optional<OtpInfo> findByUserContactAndContactTypeAndOtpTypeAndIsActiveTrue(String userContact, ContactType contactType, OtpType otpType);

}
