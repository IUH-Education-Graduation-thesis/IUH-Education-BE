package com.hong_hoan.iuheducation.resolvers.input.khoa_hoc;

import lombok.*;

import java.util.Objects;
import java.util.stream.Stream;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FindKhoaHocInputs {
    private Long id;
    private Integer tenKhoaHoc;
    private Long chuyenNganhId;
    private Long khoaVienId;
    private Integer page;
    private Integer size;

    public boolean checkBaseValuesEmpty() {
        return Stream.of(id, tenKhoaHoc).allMatch(Objects::isNull);
    }

    public boolean checkPaginationEmpty() {
        return Stream.of(page, size).allMatch(Objects::isNull);
    }

    public boolean allDataIsEmpty() {
        return Stream.of(id, tenKhoaHoc, chuyenNganhId, khoaVienId, page, size).allMatch(Objects::isNull);
    }
}
