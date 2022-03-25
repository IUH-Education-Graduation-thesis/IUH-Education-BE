package com.hong_hoan.iuheducation.exception;

public class KhoaVienIsNotExistException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Khoa viện không tồn tại";
    }
}
