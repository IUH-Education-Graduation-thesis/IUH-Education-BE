package com.hong_hoan.iuheducation.resolvers.input.lop;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Builder
@Getter
@ToString
public class FindLopHocInputs {
    private String id;
    private String ten;
    private List<Long> chuyenNganhIds;
    private List<Long> khoaHocIds;
    private List<Long> khoaVienIds;
    private int page;
    private int size;

    public boolean checkBaseValueEmpty() {
        return Stream.of(id, ten, chuyenNganhIds, khoaHocIds, khoaVienIds).allMatch(Objects::isNull);
    }
}
