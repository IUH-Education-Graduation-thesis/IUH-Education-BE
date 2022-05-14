package com.hong_hoan.iuheducation.resolvers.input.nam_hoc;

import lombok.*;

import java.util.Objects;
import java.util.stream.Stream;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterNamHocInputs {
    private Long id;
    private Integer fromYear;
    private Integer toYear;
    private Integer page;
    private Integer size;

    public boolean isAllFieldEmpty() {
        return Stream.of(id, fromYear, toYear, page, size).allMatch(Objects::isNull);
    }

    public boolean isPaginationFieldEmpty() {
        return Stream.of(page, size).allMatch(Objects::isNull);
    }

}
