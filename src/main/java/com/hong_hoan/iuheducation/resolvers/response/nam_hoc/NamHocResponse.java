package com.hong_hoan.iuheducation.resolvers.response.nam_hoc;

import com.hong_hoan.iuheducation.entity.HocKyNormal;
import com.hong_hoan.iuheducation.entity.NamHoc;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseInterface;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
public class NamHocResponse implements ResponseInterface {
    private ResponseStatus status;
    private List<ErrorResponse> errors;
    private String message;
    private List<NamHoc> data;
}
