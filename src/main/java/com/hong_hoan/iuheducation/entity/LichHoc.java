package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lich_hoc")
public class LichHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "ngay_hoc_trong_tuan", nullable = false)
    private Integer ngayHocTrongTuan;
    @Column(name = "nhom_thuc_hanh")
    private Integer nhomThucHanh;
    @Column(nullable = false)
    private Date thoiGianBatDau;
    @Column(nullable = false)
    private Integer lapLai;
    private boolean isLichThi;

    @Column(name = "cancels")
    @ElementCollection
    @JoinTable(name = "lich_hoc_cancels", joinColumns = @JoinColumn(name = "lich_hoc_id"))
    private List<Integer> cancels = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "giang_vien_id")
    private GiangVien giangVien;

    @Column(nullable = false)
    private int tietHocBatDau;
    @Column(nullable = false)
    private int tietHocKetThuc;
    @Column(nullable = false)
    private String ghiChu;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "phong_hoc_id")
    private PhongHoc phongHoc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lop_hoc_phan_id")
    private LopHocPhan lopHocPhan;

    public boolean isLyThuyet() {
        if (this.nhomThucHanh == null) {
            return true;
        }

        return false;
    }

    public GiangVien getGiangVien() {
        if (this.giangVien == null) {
            return lopHocPhan.getGiangViens().iterator().next();
        }

        return this.giangVien;
    }

    public Date getThoiGianKetThuc() {
        Calendar _calendar = Calendar.getInstance();
        _calendar.setTime(this.thoiGianBatDau);

        _calendar.add(Calendar.DATE, this.lapLai * 7);

        return _calendar.getTime();
    }

}