package com.hong_hoan.iuheducation.resolvers.input.day_nha;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class FindDayNhaInputs {
    private String id;
    private String tenDayNha;
}
