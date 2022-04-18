package com.hong_hoan.iuheducation.resolvers.response.hoc_ky_normal;

import com.hong_hoan.iuheducation.entity.HocKyNormal;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseInterface;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import com.hong_hoan.iuheducation.resolvers.response.hoc_phan.PaginationHocPhan;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class HocKyNormalResponse implements ResponseInterface {
    private ResponseStatus status;
    private List<ErrorResponse> errors;
    private String message;
    private List<HocKyNormal> data;
}
