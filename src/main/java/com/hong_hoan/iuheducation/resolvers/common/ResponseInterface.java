package com.hong_hoan.iuheducation.resolvers.common;

import java.util.List;

public interface ResponseInterface {
    ResponseStatus status = null;
    String message = null;
    List<ErrorResponse> errors = null;
}
