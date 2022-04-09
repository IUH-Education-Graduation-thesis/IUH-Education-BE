package com.hong_hoan.iuheducation.resolvers.response.sinh_vien;

import com.hong_hoan.iuheducation.entity.SinhVien;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class SuccessAndFailSinhVien {
    private List<SinhVien> sinhVienSuccess;
    private List<SinhVien> sinhVienFailure;
}
