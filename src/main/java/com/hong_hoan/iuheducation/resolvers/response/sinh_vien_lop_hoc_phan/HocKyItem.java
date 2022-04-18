package com.hong_hoan.iuheducation.resolvers.response.sinh_vien_lop_hoc_phan;

import com.hong_hoan.iuheducation.entity.SinhVienLopHocPhan;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class HocKyItem {
    private Integer thuTuHocKy;
    private Integer namBatDau;
    private Integer namKetThuc;
    private List<SinhVienLopHocPhan> listSinhVienLopHocPhan;
}
