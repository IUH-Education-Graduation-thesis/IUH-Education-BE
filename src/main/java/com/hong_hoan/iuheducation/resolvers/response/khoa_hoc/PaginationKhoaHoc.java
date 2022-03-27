package com.hong_hoan.iuheducation.resolvers.response.khoa_hoc;

import com.hong_hoan.iuheducation.entity.Khoa;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class PaginationKhoaHoc {
    private int count;
    private List<Khoa> data;
}
