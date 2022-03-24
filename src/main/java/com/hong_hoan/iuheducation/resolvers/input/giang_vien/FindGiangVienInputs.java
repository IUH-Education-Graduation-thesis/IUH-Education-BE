package com.hong_hoan.iuheducation.resolvers.input.giang_vien;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Builder
@Getter
@ToString
public class FindGiangVienInputs {
    private String id;
    private String hoVaTen;
    private Collection<Long> khoaVienIds;
    private int page;
    private int size;

    public boolean checkBaseInputsEmpty() {
        return Stream.of(id, hoVaTen, khoaVienIds).allMatch(Objects::isNull);
    }

    public boolean checkAndConditionNotNull() {
        return Stream.of(hoVaTen, khoaVienIds).anyMatch(Objects::nonNull);
    }

}
