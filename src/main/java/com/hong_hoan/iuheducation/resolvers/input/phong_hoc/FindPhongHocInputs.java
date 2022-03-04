package com.hong_hoan.iuheducation.resolvers.input.phong_hoc;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class FindPhongHocInputs {
    private String id;
    private String dayNhaId;
}
