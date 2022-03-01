package com.hong_hoan.iuheducation.exception;

public class BadTokenException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Token không hợp lệ!";
    }
}
