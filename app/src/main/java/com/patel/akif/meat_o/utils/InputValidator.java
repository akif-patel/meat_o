package com.patel.akif.meat_o.utils;

import java.util.regex.Pattern;

/**
 * Created by akif_p on 02/06/2017.
 */

public class InputValidator {
    public static boolean isValidIndiaMobileNumber(String mobileNumber) {
        Pattern mobilePattern = Pattern.compile("^[7-9][0-9]{9}$");

        return mobilePattern.matcher(mobileNumber).matches();
    }

    public static boolean isValidPinCode(String pinCode) {
        Pattern zipPattern = Pattern.compile("^[1-9][0-9]{5}$");

        return zipPattern.matcher(pinCode).matches();
    }
}
