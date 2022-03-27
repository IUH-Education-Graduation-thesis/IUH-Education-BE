package com.hong_hoan.iuheducation.resolvers.input.khoa_vien;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.stream.Stream;

@Builder
@Getter
@ToString
public class FindKhoaVienInputs {
    private String id;
    private String ten;
    private int page;
    private int size;

    public boolean checkBaseDateEmpty() {
        return Stream.of(id, ten).allMatch(Objects::isNull);
    }
}
