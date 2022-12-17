package com.jb.app.exceptions;

import lombok.Getter;

public enum ExceptionType {
    EMAIL_ALREADY_EXISTS("email already exists"),
    UNAUTHORIZED_ACTION("this action is off limits for user type"),
    ALREADY_PURCHASED("you already owm this coupon"),
    COUPON_EXPIRED("coupon purchase time has expired"),
    OUT_OF_STOCK("out of stock"),
    COMPANY_NAME_ALREADY_EXISTS(""),
    COUPON_NAME_ALREADY_EXISTS("chosen title cannot be chosen as it already exists for this company"),
    INVALID_CHANGE_REQUEST("tried changing illegal attributes"),
    INVALID_EMAIL_AND_PASSWORD("email and/or password were incorrect"),
    INCORRECT_LOGIN("login failed, please try again"),
    NO_SUCH_ID("id requested was not found in database"),
    ID_ALREADY_EXITS("cannot create an object with an existing id number"),
    INVALID_EMAIL("email address sent is invalid"),
    INVALID_PASSWORD("Password is invalid, Secure Password requirements:\n" +
            "\n" +
            "Password must contain at least one digit [0-9].\n" +
            "Password must contain at least one lowercase Latin character [a-z].\n" +
            "Password must contain at least one uppercase Latin character [A-Z].\n" +
            "Password must contain a length of at least 8 characters and a maximum of 20 characters."),
    UNAUTHORIZED_TOKEN("login token has timed out"),
    HTTP_NOT_FOUND("page not found"),
    INVALID_DATE("expiration date set before the start date.")

    ;
    @Getter
    private String msg;

    ExceptionType(String msg) {
        this.msg = msg;
    }
}
