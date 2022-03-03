package com.hong_hoan.iuheducation.exception;

public class DayNhaIsNotExistException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Dãy nhà không tồn tại!";
    }
}
