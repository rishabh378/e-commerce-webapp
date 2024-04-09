package com.pesto.ecommerce.models.request;

import com.pesto.ecommerce.enums.ContactType;
import com.pesto.ecommerce.enums.OtpType;
import com.pesto.ecommerce.validators.LoginValidators;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Code is a mandatory field", groups = LoginValidators.Google.class)
    private String code;

    @NotNull(message = "Contact type should not be empty", groups = LoginValidators.OTP.class)
    private ContactType contactType;  // EMAIL, PHONE

    @NotNull(message = "User contact should not be empty", groups = LoginValidators.OTP.class)
    private String userContact;

    @NotNull(message = "Otp type should not be empty", groups = LoginValidators.OTP.class)
    private OtpType otpType;  // LOGIN, FORGOT

    private String otp;

}
