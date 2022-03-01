package com.hong_hoan.iuheducation.resolvers.response.account;

import com.hong_hoan.iuheducation.entity.Account;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RegisterResponse {
    private ResponseStatus status;
    private List<ErrorResponse> errors;
    private String message;
    private Account data;
}
