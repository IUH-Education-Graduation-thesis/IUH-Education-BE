package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sinh_vien")
public class SinhVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String maSinhVien;

    @Column(unique = true, nullable = false)
    private String maHoSo;
    private String avatar;

    @Column(nullable = false)
    private String hoTenDem;
    @Column(nullable = false)
    private String ten;
    private boolean gioiTinh;
    private Date ngayVaoDang;
    private Date ngayVaoTruong;
    private Date ngayVaoDoan;
    private Date ngaySinh;
    private String soDienThoai;
    private String diaChi;
    private String noiSinh;
    private String hoKhauThuongTru;
    private String email;
    private String soCMND;

    @Enumerated(EnumType.STRING)
    @Column(name = "bac_dao_tao")
    private BacDaoTao bacDaoTao;

    public String bacDaoTaoString() {
        return bacDaoTao.getName();
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai")
    private TrangThai trangThai;

    public String trangThaiString() {
        return trangThai.getName();
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "dan_toc")
    private DanToc danToc;

    public String danTocString() {
        return danToc.getName();
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "ton_giao")
    private TonGiao tonGiao;

    public String tonGiaoString() {
        return tonGiao.getName();
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "loai_hinh_dao_tao")
    private LoaiHinhDaoTao loaiHinhDaoTao;

    public String loaiHinhDaoTaoString() {
        return loaiHinhDaoTao.getName();
    }

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "lop_id")
    private Lop lop;

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SinhVienLopHocPhan> sinhVienLopHocPhans = new ArrayList<>();

}