package com.hong_hoan.iuheducation.exception;

public class ChuyenNganhIsNotExistExcepton extends RuntimeException{
    @Override
    public String getMessage() {
        return "Chuyên ngành không tồn tại!";
    }
}
