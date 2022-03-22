package com.hong_hoan.iuheducation.resolvers.input.khoa_hoc;

import lombok.*;

import java.util.Objects;
import java.util.stream.Stream;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class FindKhoaHocInputs {
    private String id;
    private int tenKhoaHoc;
    private int page;
    private int size;

    public boolean checkBaseValuesEmpty() {
        return Stream.of(id, tenKhoaHoc).allMatch(Objects::isNull);
    }
}
