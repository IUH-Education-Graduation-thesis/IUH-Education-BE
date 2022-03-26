package com.hong_hoan.iuheducation.resolvers.response.chuyen_nganh;

import com.hong_hoan.iuheducation.entity.ChuyenNganh;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseInterface;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
@Builder
@Getter
@ToString
public class ChuyenNganhResponse implements ResponseInterface {
    private ResponseStatus status;
    private List<ErrorResponse> errors;
    private String message;
    private List<ChuyenNganh> data;
}
