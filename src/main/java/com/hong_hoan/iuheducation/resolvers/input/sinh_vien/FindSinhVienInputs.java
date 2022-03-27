package com.hong_hoan.iuheducation.resolvers.input.sinh_vien;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Builder
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
    private int page;
    private int size;

    public boolean checkPaginationEmpty() {
        return Stream.of(page, size).anyMatch(Objects::isNull);
    }
}
