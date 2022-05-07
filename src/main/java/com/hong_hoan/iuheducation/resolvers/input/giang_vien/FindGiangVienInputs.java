package com.hong_hoan.iuheducation.resolvers.input.giang_vien;

import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FindGiangVienInputs {
    private Long id;
    private String hoVaTen;
    private Collection<Long> khoaVienIds;
    private Integer page;
    private Integer size;

    public boolean checkBaseInputsEmpty() {
        return Stream.of(id, hoVaTen, khoaVienIds).allMatch(Objects::isNull);
    }

    public boolean checkAllFieldNull() {
        return Stream.of(id, hoVaTen, khoaVienIds, page, size).allMatch(Objects::isNull);
    }

    public boolean checkPaginationVariableNull() {
        return Stream.of(page, size).allMatch(Objects::isNull);
    }

    public boolean checkAndConditionNotNull() {
        return Stream.of(hoVaTen, khoaVienIds).anyMatch(Objects::nonNull);
    }

}
