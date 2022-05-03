package com.hong_hoan.iuheducation.resolvers.input.hoc_phan;

import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FindHocPhanInputs {
    private Long id;
    private String maHocPhan;
    private List<Long> hocKyIds;
    private List<Long> khoaIds;
    private List<Long> chuyenNganhIds;
    private List<Long> khoaVienIds;
    private List<Long> monHocIds;
    private Integer page;
    private Integer size;

    public boolean checkAllDataIsNull() {
        return Stream.of(id, maHocPhan, hocKyIds, khoaVienIds, monHocIds, page, size, khoaIds, chuyenNganhIds).allMatch(Objects::isNull);
    }

    public boolean checkPaginationIsNull() {
        return Stream.of(page, size).anyMatch(Objects::isNull);
    }
}
