package com.hong_hoan.iuheducation.resolvers.response.nam_hoc;

import com.hong_hoan.iuheducation.entity.NamHoc;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class FindNamHocRest {
    private long count;
    private List<NamHoc> data;
}
