package com.pesto.ecommerce.utils;

import com.pesto.ecommerce.constants.ApplicationConstants;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

@UtilityClass
public class CommonUtils {

    public String generateUUID(String name) {
        String formattedName = name.toLowerCase().replaceAll("\\s+", "");
        // Generate a UUID based on the formatted name
        UUID uuid = UUID.randomUUID();
        // Convert the UUID to a string
        String uuidString = uuid.toString().substring(0, 8);
        // Remove any hyphens from the generated UUID
        uuidString = uuidString.replaceAll("-", "");
        // Return the generated UUID
        return formattedName + "-" + uuidString;
    }

    public static boolean isValidEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    public static String generateUserOtp(int length) {
        Random randomMethod = new Random();
        String allowedCharacters = ApplicationConstants.ALLOWED_OTP_CHARACTERS;

        char[] otp = new char[length];

        for (int i = 0; i < length; i++) {
            otp[i] = allowedCharacters.charAt(randomMethod.nextInt(allowedCharacters.length()));
        }
        return String.valueOf(otp);
    }

    public static String generateUserId() {
        return UUID.randomUUID().toString();
    }

}
