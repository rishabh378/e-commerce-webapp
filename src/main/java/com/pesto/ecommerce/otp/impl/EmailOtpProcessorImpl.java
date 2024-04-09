package com.pesto.ecommerce.otp.impl;

import com.pesto.ecommerce.enums.ContactType;
import com.pesto.ecommerce.exception.ApplicationException;
import com.pesto.ecommerce.otp.OtpProcessor;
import com.pesto.ecommerce.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class EmailOtpProcessorImpl implements OtpProcessor {


    @Override
    public ContactType getContactType() {
        return ContactType.EMAIL;
    }


    @Override
    public void validateContact(String email) {
        if (!CommonUtils.isValidEmail(email))
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Please enter valid emailId");
    }

}
