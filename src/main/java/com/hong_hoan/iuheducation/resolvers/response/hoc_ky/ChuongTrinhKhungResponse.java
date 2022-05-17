package com.hong_hoan.iuheducation.resolvers.response.hoc_ky;

import com.hong_hoan.iuheducation.entity.HocKy;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseInterface;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChuongTrinhKhungResponse implements ResponseInterface {
    private ResponseStatus status;
    private List<ErrorResponse> errors;
    private String message;
    private List<HocKyChuongTrinhKhung> data;
}
