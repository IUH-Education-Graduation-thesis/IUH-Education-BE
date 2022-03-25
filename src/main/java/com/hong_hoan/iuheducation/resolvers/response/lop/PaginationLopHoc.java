package com.hong_hoan.iuheducation.resolvers.response.lop;

import com.hong_hoan.iuheducation.entity.Lop;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class PaginationLopHoc {
    private int count;
    private List<Lop> data;
}
