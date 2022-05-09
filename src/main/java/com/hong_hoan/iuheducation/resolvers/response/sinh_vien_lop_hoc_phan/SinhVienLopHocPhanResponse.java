package com.hong_hoan.iuheducation.resolvers.response.sinh_vien_lop_hoc_phan;

import com.hong_hoan.iuheducation.entity.SinhVienLopHocPhan;
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
@ToString
@Setter
public class SinhVienLopHocPhanResponse implements ResponseInterface {
    private ResponseStatus status;
    private List<ErrorResponse> errors;
    private String message;
    private List<SinhVienLopHocPhan> data;
}
