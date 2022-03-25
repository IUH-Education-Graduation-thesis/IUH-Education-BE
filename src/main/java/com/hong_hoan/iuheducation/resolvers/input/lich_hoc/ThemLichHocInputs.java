package com.hong_hoan.iuheducation.resolvers.input.lich_hoc;

import com.hong_hoan.iuheducation.entity.LopHocPhan;
import com.hong_hoan.iuheducation.entity.PhongHoc;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.Date;

@Builder
@Getter
@ToString
public class ThemLichHocInputs {
    private int ngayHocTrongTuan;
    private int nhomThucHanh;
    private Date thoiGianBatDau;
    private Date thoiGianKetThuc;
    private int tietHocBatDau;
    private int tietHocKetThuc;
    private String ghiChu;
    private PhongHoc phongHoc;
    private LopHocPhan lopHocPhan;
}
