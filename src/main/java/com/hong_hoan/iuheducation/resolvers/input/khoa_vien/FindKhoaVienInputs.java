package com.hong_hoan.iuheducation.resolvers.input.khoa_vien;

import lombok.*;

import java.util.Objects;
import java.util.stream.Stream;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FindKhoaVienInputs {
    private String id;
    private String ten;
    private Integer page;
    private Integer size;

    public boolean checkBaseDateEmpty() {
        return Stream.of(id, ten).allMatch(Objects::isNull);
    }

    public boolean checkPaginationEmpty() {
        return Stream.of(page, size).anyMatch(Objects::isNull);
    }
}
