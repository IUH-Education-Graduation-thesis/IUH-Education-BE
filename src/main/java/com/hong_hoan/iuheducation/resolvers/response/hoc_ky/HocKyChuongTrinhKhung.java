package com.hong_hoan.iuheducation.resolvers.response.hoc_ky;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class HocKyChuongTrinhKhung {
    private Integer hocKy;
    private Integer tongSoTinChi;
    private List<HocPhanCustomize> hocPhansRes;

}
