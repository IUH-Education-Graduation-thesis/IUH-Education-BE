package com.hong_hoan.iuheducation.entity;

import com.hong_hoan.iuheducation.util.HelperComponent;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sinh_vien_lop_hoc_phan")
public class SinhVienLopHocPhan {
    @EmbeddedId
    private SinhVienLopHocPhanId sinhVienLopHocPhanId;

    @ElementCollection
    @Column(name = "diem_thuong_ky")
    @CollectionTable(name = "sinh_vien_lop_hoc_phan_diem_thuong_ky")
    private List<Double> diemThuongKy = new ArrayList<>();

    private Double diemGiuaKy;

    @ElementCollection
    @Column(name = "diem_thuc_hanh")
    @CollectionTable(name = "sinh_vien_lop_hoc_phan_diem_thuc_hanh")
    private List<Double> diemThucHanh = new ArrayList<>();

    private Double diemCuoiKy;
    private String ghiChu;
    private Integer nhomThucHanh;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @MapsId("sinhVienId")
    @JoinColumn(name = "sinh_vien_id")
    private SinhVien sinhVien;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @MapsId("lopHocPhanId")
    @JoinColumn(name = "lop_hoc_phan_id")
    private LopHocPhan lopHocPhan;

    public Double getDiemTrungBinh() {
        if (this.getDiemCuoiKy() == null) {
            return null;
        }

        Double _trungBinhLyThuyet = 0.0;

        try {
            _trungBinhLyThuyet = this.getDiemThuongKy().stream().mapToDouble(a -> a).average().getAsDouble();
        } catch (NoSuchElementException ex) {

        }

        double _tongLyThuyet = (_trungBinhLyThuyet * 20 + this.getDiemGiuaKy() * 30 + this.getDiemCuoiKy() * 50) / 100;

        HocPhan _hocPhan = this.getLopHocPhan().getHocPhan();

        if (_hocPhan.getSoTinChiThucHanh() <= 0) {
            return _tongLyThuyet;
        }

        Double _trungBinhThucHanh = 0.0;

        try {
            _trungBinhThucHanh = this.getDiemThucHanh().stream().mapToDouble(a -> a).average().getAsDouble();
        } catch (NoSuchElementException ex) {

        }

        return ((_tongLyThuyet * _hocPhan.getSoTinChiLyThuyet()) + (_trungBinhThucHanh * _hocPhan.getSoTinChiThucHanh())) / (_hocPhan.getSoTinChiLyThuyet() + _hocPhan.getSoTinChiThucHanh());
    }

}