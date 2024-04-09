package com.pesto.ecommerce.otp;

import com.pesto.ecommerce.enums.ContactType;

public interface OtpProcessor {

    ContactType getContactType();

    void validateContact(String userContact);

}
