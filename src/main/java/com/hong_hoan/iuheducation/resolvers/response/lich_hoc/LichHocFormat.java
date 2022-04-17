package com.hong_hoan.iuheducation.resolvers.response.lich_hoc;

import com.hong_hoan.iuheducation.entity.LichHoc;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
public class LichHocFormat {
    private String thu;
    private int thuNumber;
    private List<LichHoc> listLichHoc;
}
