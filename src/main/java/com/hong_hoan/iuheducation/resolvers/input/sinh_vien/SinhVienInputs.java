package com.hong_hoan.iuheducation.resolvers.input.sinh_vien;

import com.hong_hoan.iuheducation.entity.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
public class SinhVienInputs {
    private String hoTenDem;
    private String ten;
    private String avatar;
    private boolean gioiTinh;
    private Date ngayVaoDang;
    private Date ngayVaoTruong;
    private Date ngayVaoDoan;
    private Date ngaySinh;
    private String soDienThoai;
    private String diaChi;
    private String noiSinh;
    private String email;
    private String soCMND;
    private BacDaoTao bacDaoTao;
    private TrangThai trangThai;
    private DanToc danToc;
    private TonGiao tonGiao;
    private LoaiHinhDaoTao loaiHinhDaoTao;
    private Long lopId;
}
