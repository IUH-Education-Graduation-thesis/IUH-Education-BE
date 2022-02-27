package com.hong_hoan.iuheducation.resolvers.response.account;

import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseInterface;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class LoginResponse implements ResponseInterface {
    private ResponseStatus status;
    private List<ErrorResponse> errors;
    private String message;
    private LoginData data;
}
