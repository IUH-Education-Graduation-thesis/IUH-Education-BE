package com.hong_hoan.iuheducation.resolvers.response.nam_hoc;

import com.hong_hoan.iuheducation.entity.NamHoc;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class PaginationNamHoc {
    private Integer count;
    private List<NamHoc> result;
}
