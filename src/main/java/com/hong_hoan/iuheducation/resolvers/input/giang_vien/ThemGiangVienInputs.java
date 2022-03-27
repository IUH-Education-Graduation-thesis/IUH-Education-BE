package com.hong_hoan.iuheducation.resolvers.input.giang_vien;

import com.hong_hoan.iuheducation.entity.HocHam;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ThemGiangVienInputs {
    private String hoTenDem;
    private String ten;
    private String avatar;
    private boolean gioiTinh;
    private String soDienThoai;
    private String email;
    private HocHam hocHam;
    private long chuyenNganhID;
}
