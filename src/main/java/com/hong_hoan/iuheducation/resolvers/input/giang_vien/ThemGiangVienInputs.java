package com.hong_hoan.iuheducation.resolvers.input.giang_vien;

import com.hong_hoan.iuheducation.entity.HocHam;
import lombok.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
