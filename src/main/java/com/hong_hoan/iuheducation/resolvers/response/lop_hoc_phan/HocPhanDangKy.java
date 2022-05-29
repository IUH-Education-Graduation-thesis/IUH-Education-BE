package com.hong_hoan.iuheducation.resolvers.response.lop_hoc_phan;

import com.hong_hoan.iuheducation.entity.SinhVienLopHocPhan;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class HocPhanDangKy {
    private List<SinhVienLopHocPhan> sinhVienLopHocPhans;
    private List<LopHocPhanFailure> lopHocPhanFailures;
}
