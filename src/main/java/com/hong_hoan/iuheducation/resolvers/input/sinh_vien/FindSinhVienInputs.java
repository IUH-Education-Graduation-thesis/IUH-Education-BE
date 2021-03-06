package com.hong_hoan.iuheducation.resolvers.input.sinh_vien;

import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class FindSinhVienInputs {
    private String id;
    private String maSinhVien;
    private String tenSinhVien;
    private List<Long> khoaVienIds;
    private List<Long> chuyenNganhIds;
    private List<Long> khoaHocIds;
    private List<Long> lopIds;
    private Integer page;
    private Integer size;

    public boolean allDataIsEmpty() {
        return Stream.of(id, maSinhVien, tenSinhVien, khoaVienIds, chuyenNganhIds, khoaHocIds, lopIds, page, size).allMatch(Objects::isNull);
    }

    public boolean checkPaginationEmpty() {
        return Stream.of(page, size).anyMatch(Objects::isNull);
    }
}
