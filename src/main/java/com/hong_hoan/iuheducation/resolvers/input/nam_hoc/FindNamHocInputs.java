package com.hong_hoan.iuheducation.resolvers.input.nam_hoc;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;

@Builder
@Getter
@ToString
public class FindNamHocInputs {
    private String id;
    private Date formDate;
    private Date toDate;

    private int page;
    private int size;

    public boolean checkIsNull() {
        return Stream.of(id, formDate, toDate).allMatch(Objects::isNull);
    }
}
