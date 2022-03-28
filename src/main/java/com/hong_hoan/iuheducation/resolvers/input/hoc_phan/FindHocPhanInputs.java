package com.hong_hoan.iuheducation.resolvers.input.hoc_phan;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Builder
@Getter
@ToString
public class FindHocPhanInputs {
    private String id;
    private String maHocPhan;
    private List<Long> namHocIds;
    private List<Long> hocKyIds;
    private List<Long> khoaVienIds;
    private List<Long> monHocIds;
    private Integer page;
    private Integer size;

    public boolean checkAllDataIsNull() {
        return Stream.of(id, maHocPhan, namHocIds, hocKyIds, khoaVienIds, monHocIds, page, size).allMatch(Objects::isNull);
    }

    public boolean checkPaginationIsNull() {
        return Stream.of(page, size).anyMatch(Objects::isNull);
    }
}
