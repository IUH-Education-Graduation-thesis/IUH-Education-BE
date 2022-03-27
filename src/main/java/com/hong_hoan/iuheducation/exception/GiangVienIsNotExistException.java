package com.hong_hoan.iuheducation.exception;

public class GiangVienIsNotExistException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Giảng viên không tồn tại";
    }
}
