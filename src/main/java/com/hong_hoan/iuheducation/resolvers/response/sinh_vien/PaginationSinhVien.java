package com.hong_hoan.iuheducation.resolvers.response.sinh_vien;

import com.hong_hoan.iuheducation.entity.SinhVien;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PaginationSinhVien {
    private int count;
    private List<SinhVien> data;
}
