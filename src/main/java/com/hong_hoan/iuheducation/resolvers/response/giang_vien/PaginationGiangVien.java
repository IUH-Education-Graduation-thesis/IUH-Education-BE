package com.hong_hoan.iuheducation.resolvers.response.giang_vien;

import com.hong_hoan.iuheducation.entity.GiangVien;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class PaginationGiangVien {
    private int count;
    private List<GiangVien> data;

}
