package com.hong_hoan.iuheducation.resolvers.input.day_nha;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SuaDayNhaInput {
    private long id;
    private String tenDayNha;
    private String moTa;
}
