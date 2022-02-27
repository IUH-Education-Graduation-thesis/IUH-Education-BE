package com.hong_hoan.iuheducation.exception;

import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;

import java.text.MessageFormat;

@AllArgsConstructor
public class UserAlreadyExistsException extends RuntimeException {
    private final String name;

    @Override
    public String getMessage() {
        return MessageFormat.format("Người dùng đã tồn tại với username: {{0}}", name);
    }
}
