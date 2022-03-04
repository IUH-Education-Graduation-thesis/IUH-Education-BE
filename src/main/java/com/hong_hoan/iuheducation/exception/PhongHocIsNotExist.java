package com.hong_hoan.iuheducation.exception;

public class PhongHocIsNotExist extends RuntimeException {
    @Override
    public String getMessage() {
        return "Phòn học không tồn tại!";
    }
}
