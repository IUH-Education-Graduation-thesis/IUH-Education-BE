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
        try {
            return bacDaoTao.getName();
        } catch (NullPointerException ex) {
            return "";
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai")
    private TrangThai trangThai;

    public String trangThaiString() {
        try {
            return trangThai.getName();
        } catch (NullPointerException ex) {
            return "";
        }

    }

    @Enumerated(EnumType.STRING)
    @Column(name = "dan_toc")
    private DanToc danToc;

    public String danTocString() {
        try {
            return danToc.getName();
        } catch (NullPointerException ex) {
            return "";
        }

    }

    @Enumerated(EnumType.STRING)
    @Column(name = "ton_giao")
    private TonGiao tonGiao;

    public String tonGiaoString() {
        try {
            return tonGiao.getName();
        } catch (NullPointerException ex) {
            return "";
        }

    }

    @Enumerated(EnumType.STRING)
    @Column(name = "loai_hinh_dao_tao")
    private LoaiHinhDaoTao loaiHinhDaoTao;

    public String loaiHinhDaoTaoString() {
        try {
            return loaiHinhDaoTao.getName();

        } catch (NullPointerException ex) {
            return "";
        }
    }

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "lop_id")
    private Lop lop;

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<SinhVienLopHocPhan> sinhVienLopHocPhans = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private Account account;

}