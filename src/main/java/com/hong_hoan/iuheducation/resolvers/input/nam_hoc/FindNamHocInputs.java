package com.hong_hoan.iuheducation.resolvers.input.nam_hoc;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Builder
@Getter
@ToString
public class FindNamHocInputs {
    private String id;
    private Date formDate;
    private Date toDate;
}
