package com.hong_hoan.iuheducation.resolvers.response.lop_hoc_phan;

import com.hong_hoan.iuheducation.entity.LopHocPhan;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class LopHocPhanFailure {
    private LopHocPhan lopHocPhan;
    private String message;
}
