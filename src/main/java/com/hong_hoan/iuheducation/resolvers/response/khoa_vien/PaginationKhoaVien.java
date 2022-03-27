package com.hong_hoan.iuheducation.resolvers.response.khoa_vien;

import com.hong_hoan.iuheducation.entity.KhoaVien;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class PaginationKhoaVien {
    private int count;
    private List<KhoaVien> data;
}
