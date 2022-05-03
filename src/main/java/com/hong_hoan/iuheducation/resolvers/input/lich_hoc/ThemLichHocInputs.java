package com.hong_hoan.iuheducation.resolvers.input.lich_hoc;

import com.hong_hoan.iuheducation.entity.LopHocPhan;
import com.hong_hoan.iuheducation.entity.PhongHoc;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ThemLichHocInputs {
    private Integer ngayHocTrongTuan;
    private Integer nhomThucHanh;
    private Date thoiGianBatDau;
    private Integer tietHocBatDau;
    private Integer tietHocKetThuc;
    private String ghiChu;
    private Long phongHocId;
    private Long lopHocPhanId;
    private Long giangVienId;
    private Boolean isLichThi;
    private Boolean isHocBu;
}
