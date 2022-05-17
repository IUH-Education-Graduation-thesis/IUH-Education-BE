package com.hong_hoan.iuheducation.resolvers.response.hoc_ky;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HocPhanCustomize {
    private String maMonHoc;
    private String tenMonHoc;
    private Integer tinChi;
    private Boolean trangThai;
}
