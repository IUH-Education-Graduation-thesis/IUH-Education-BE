package com.hong_hoan.iuheducation.resolvers.response.lop_hoc_phan;

import com.hong_hoan.iuheducation.entity.LichHoc;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class NgayTrongTuan {
    private String thu;
    private Integer thuNumber;

    private List<LichHoc> lichHocs;

}
