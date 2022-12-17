package com.jb.app.utils;

import java.util.Locale;
import java.util.regex.Pattern;

//final class: can't be inherited
public final class ValidationUtils {

    /*
    The following restrictions are imposed in the email address' local part by using this regex:

It allows numeric values from 0 to 9.
Both uppercase and lowercase letters from a to z are allowed.
Allowed are underscore “_”, hyphen “-“, and dot “.”
Dot isn't allowed at the start and end of the local part.
Consecutive dots aren't allowed.
For the local part, a maximum of 64 characters are allowed.
Restrictions for the domain part in this regular expression include:


    */

    private  ValidationUtils () {} //no need for singleton, this class only has static methods and no fields. private ctor is enough to instance proof from non-malicious sources


    public static boolean isValidEmail(String email) {
        String regexPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"; //stackoverflow shit, comments really like it.
        return  (Pattern.matches(regexPattern, email.toLowerCase()));
    }

    public static boolean isValidPassword(String password) {
        //Secure Password requirements
        //
        //Password must contain at least one digit [0-9].
        //Password must contain at least one lowercase Latin character [a-z].
        //Password must contain at least one uppercase Latin character [A-Z].
        //Password must contain a length of at least 8 characters and a maximum of 20 characters.
        String regexPattern =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

        return Pattern.matches(regexPattern,password);
    }
}

