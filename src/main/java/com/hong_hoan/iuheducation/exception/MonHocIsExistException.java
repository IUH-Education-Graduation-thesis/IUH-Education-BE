package com.hong_hoan.iuheducation.exception;

import java.util.concurrent.ExecutionException;

public class MonHocIsExistException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Mon hoc khong ton tai";
    }
}
