package com.jb.app.exceptions;

public class CouponSysException extends Exception{

    public CouponSysException(ExceptionType type) {
        super(type.getMsg());
    }

    public CouponSysException(String message) {
        super(message);
    }
}
