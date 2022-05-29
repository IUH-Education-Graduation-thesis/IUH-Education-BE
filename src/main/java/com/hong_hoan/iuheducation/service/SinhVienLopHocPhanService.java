package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.*;
import com.hong_hoan.iuheducation.exception.LopHocPhanIsNotExist;
import com.hong_hoan.iuheducation.exception.SinhVienLopHocPhanIsNotExist;
import com.hong_hoan.iuheducation.repository.LopHocPhanRepository;
import com.hong_hoan.iuheducation.repository.SinhVienLopHocPhanRepository;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.DangKyHocPhanInputs;
import com.hong_hoan.iuheducation.resolvers.input.sinh_vien_lop_hoc_phan.SuaSinhVienLopHocPhanInputs;
import com.hong_hoan.iuheducation.resolvers.response.lop_hoc_phan.HocPhanDangKy;
import com.hong_hoan.iuheducation.resolvers.response.lop_hoc_phan.LopHocPhanFailure;
import com.hong_hoan.iuheducation.resolvers.response.sinh_vien_lop_hoc_phan.HocKyItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SinhVienLopHocPhanService {
    @Autowired
    private SinhVienLopHocPhanRepository sinhVienLopHocPhanRepository;
    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;
    @Autowired
    private HocKyNormalService hocKyNormalService;

    public SinhVienLopHocPhan suaDiemSinhVien(SuaSinhVienLopHocPhanInputs inputs) {
        Optional<SinhVienLopHocPhan> _sinhVienLopHocPhanOpt = sinhVienLopHocPhanRepository.findById(SinhVienLopHocPhanId.builder()
                .sinhVienId(inputs.getSinhVienId())
                .lopHocPhanId(inputs.getLopHocPhanId())
                .build());

        if (_sinhVienLopHocPhanOpt.isEmpty()) {
            throw new SinhVienLopHocPhanIsNotExist();
        }

        SinhVienLopHocPhan _sinhVienLopHocPhan = _sinhVienLopHocPhanOpt.get();

        if (_sinhVienLopHocPhan == null) {
            throw new SinhVienLopHocPhanIsNotExist();
        }

        try {
            _sinhVienLopHocPhan.setDiemThuongKy(inputs.getDiemThuongKy());
        } catch (Exception ex) {

        }

        try {
            _sinhVienLopHocPhan.setDiemGiuaKy(inputs.getDiemGiuaKy());
        } catch (Exception ex) {

        }

        try {
            _sinhVienLopHocPhan.setDiemThucHanh(inputs.getDiemThucHanh());
        } catch (Exception ex) {

        }

        try {
            _sinhVienLopHocPhan.setDiemThucHanh(inputs.getDiemThucHanh());
        } catch (Exception ex) {

        }

        try {
            _sinhVienLopHocPhan.setDiemCuoiKy(inputs.getDiemCuoiKy());
        } catch (Exception ex) {

        }

        try {
            _sinhVienLopHocPhan.setGhiChu(inputs.getGhiChu());
        } catch (Exception ex) {

        }

        SinhVienLopHocPhan _sinhVienLopHocPhanRes = sinhVienLopHocPhanRepository.saveAndFlush(_sinhVienLopHocPhan);


        return _sinhVienLopHocPhanRes;
    }

    public List<HocKyItem> getSinhVienLopHocPhanOfSinhVien(Account account) {
        List<HocKyNormal> _hocKyNormal = hocKyNormalService.getListHocKyNormalOfSinhVien(account);

        List<HocKyItem> _hocKyItem = _hocKyNormal.stream().map(i -> {
            List<SinhVienLopHocPhan> _listSinhVienLopHocPhan = sinhVienLopHocPhanRepository.getSinhVienLopHocPhansBySinhVienIdAndHocKyId(account.getSinhVien().getId(), i.getId());

            return HocKyItem.builder()
                    .thuTuHocKy(i.getThuTuHocKy())
                    .namBatDau(i.getNamHoc().getNamBatDau())
                    .namKetThuc(i.getNamHoc().getNamKetThuc())
                    .listSinhVienLopHocPhan(_listSinhVienLopHocPhan)
                    .build();
        }).collect(Collectors.toList());

        return _hocKyItem;
    }

    public HocPhanDangKy themSinhVienLopHocPhan(List<DangKyHocPhanInputs> inputs, Account account) {
        List<SinhVienLopHocPhan> _sinhVienLopHocPhans = new ArrayList<>();

        List<LopHocPhanFailure> _lopHocPhanFailure = new ArrayList<>();


        inputs.forEach(i -> {
            Optional<LopHocPhan> _lopHocPhanOption = lopHocPhanRepository.findById(i.getLopHocPhanId());

            if (_lopHocPhanOption.isEmpty()) {
                return;
            }

            LopHocPhan _lopHocPhan = _lopHocPhanOption.get();


            if (_lopHocPhan.getTrangThaiLopHocPhanEnum() != TrangThaiLopHocPhan.CHO_SINH_VIEN_DANG_KY
                    || _lopHocPhan.getTrangThaiLopHocPhanEnum() != TrangThaiLopHocPhan.CHAP_NHAN_MO_LOP
            ) {
                _lopHocPhanFailure.add(LopHocPhanFailure.builder()
                        .lopHocPhan(_lopHocPhan)
                        .message("Lớp học phần không được phép đăng ký!")
                        .build());
                return;
            }

            if (i.getNhomThucHanh() != null) {
                Integer _soSinhVienCuaMotNhomThucHanh = Math.round(_lopHocPhan.getSoLuongToiDa() / _lopHocPhan.getSoNhomThucHanh());
                long _currentSoLuongSinhVienCuaNhomThucHanh = _lopHocPhan.getSinhVienLopHocPhans().stream()
                        .filter(item -> item.getNhomThucHanh() == i.getNhomThucHanh()).count();

                if (_currentSoLuongSinhVienCuaNhomThucHanh >= _soSinhVienCuaMotNhomThucHanh) {
                    _lopHocPhanFailure.add(LopHocPhanFailure.builder()
                            .lopHocPhan(_lopHocPhan)
                            .message("Nhóm thực hành đã đủ số lượng!")
                            .build());

                    return;
                }
            }

            if (_lopHocPhan.getSinhVienLopHocPhans().size() >= _lopHocPhan.getSoLuongToiDa()) {
                _lopHocPhanFailure.add(LopHocPhanFailure.builder()
                        .lopHocPhan(_lopHocPhan)
                        .message("Lớp học phần đã đủ số lượng!")
                        .build());

                return;
            }

            SinhVienLopHocPhanId _sinhVienLopHocPhanId = SinhVienLopHocPhanId.builder()
                    .lopHocPhanId(_lopHocPhan.getId())
                    .sinhVienId(account.getSinhVien().getId())
                    .build();

            SinhVienLopHocPhan _sinhVienLopHocPhan = SinhVienLopHocPhan.builder()
                    .sinhVienLopHocPhanId(_sinhVienLopHocPhanId)
                    .sinhVien(account.getSinhVien())
                    .lopHocPhan(_lopHocPhan)
                    .nhomThucHanh(i.getNhomThucHanh())
                    .build();

            _sinhVienLopHocPhans.add(_sinhVienLopHocPhan);
        });

        List<SinhVienLopHocPhan> _sinhVienLopHocPhanRes = sinhVienLopHocPhanRepository.saveAllAndFlush(_sinhVienLopHocPhans);

        return HocPhanDangKy.builder()
                .sinhVienLopHocPhans(_sinhVienLopHocPhanRes)
                .lopHocPhanFailures(_lopHocPhanFailure)
                .build();
    }

}
