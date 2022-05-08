package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.*;
import com.hong_hoan.iuheducation.exception.*;
import com.hong_hoan.iuheducation.repository.*;
import com.hong_hoan.iuheducation.resolvers.input.lop_hoc_phan.ThemLopHocPhanInputs;
import org.apache.commons.math3.analysis.function.Sinh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LopHocPhanService {
    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;
    @Autowired
    private HocPhanRepository hocPhanRepository;
    @Autowired
    private HocKyNormalRepository hocKyNormalRepository;
    @Autowired
    private SinhVienRepository sinhVienRepository;
    @Autowired
    private SinhVienLopHocPhanRepository sinhVienLopHocPhanRepository;

    public List<SinhVienLopHocPhan> themSinhVienVaoLopHocPhan(Long lopHocPhanId, Set<Long> sinhVienIds, Integer nhomThucHanh) {
        LopHocPhan _lopHocPhan = lopHocPhanRepository.getById(lopHocPhanId);

        if (_lopHocPhan == null) {
            throw new LopHocPhanIsNotExist();
        }

        Integer _soNhomThucHanh = _lopHocPhan.getSoNhomThucHanh();

        if(_soNhomThucHanh > 0 && nhomThucHanh == null) {
            throw new EnterNhomThucHanh();
        }

        if(nhomThucHanh > _soNhomThucHanh) {
            throw new ValueOver();
        }

        List<SinhVien> _listSinhVien = sinhVienRepository.findAllById(sinhVienIds);

        List<SinhVien> _listSinhVienInLopHocPhan = sinhVienRepository.findSinhVienInLopHocPhan(lopHocPhanId);

        List<Long> _listSinhVienInLopHocPhanId = _listSinhVienInLopHocPhan.stream().map(i -> i.getId()).collect(Collectors.toList());

        List<SinhVien> _listSinhVienFilter = _listSinhVien.stream().filter(i -> {
            Integer _indexOf = _listSinhVienInLopHocPhanId.indexOf(i.getId());

            return _indexOf < 0;
        }).collect(Collectors.toList());

        List<SinhVienLopHocPhan> _listSinhVienLopHocPhan = _listSinhVienFilter.stream().map(i -> {
            SinhVienLopHocPhanId _sinhVienLopHocPhanId = SinhVienLopHocPhanId.builder()
                    .sinhVienId(i.getId())
                    .lopHocPhanId(_lopHocPhan.getId())
                    .build();

            return SinhVienLopHocPhan.builder()
                    .sinhVienLopHocPhanId(_sinhVienLopHocPhanId)
                    .sinhVien(i)
                    .lopHocPhan(_lopHocPhan)
                    .nhomThucHanh(nhomThucHanh)
                    .build();
        }).collect(Collectors.toList());

        List<SinhVienLopHocPhan> _listSinhVienLopHocPhanRes = sinhVienLopHocPhanRepository.saveAllAndFlush(_listSinhVienLopHocPhan);

        return _listSinhVienLopHocPhanRes;
    }

    public LopHocPhan themLopHocPhan(ThemLopHocPhanInputs inputs) {
        HocPhan _hocPhan = hocPhanRepository.getById(inputs.getHocPhanId());

        if (_hocPhan == null) {
            throw new HocPhanIsNotExist();
        }

        HocKyNormal _hocKyNormal = hocKyNormalRepository.getById(inputs.getHocKyNormalId());

        if (_hocKyNormal == null) {
            throw new HocKyNormalIsNotExist();
        }

        LopHocPhan _lopHocPhan = LopHocPhan.builder()
                .hocKyNormal(_hocKyNormal)
                .hocPhan(_hocPhan)
                .maLopHocPhan(inputs.getMaLopHocPhan())
                .lopDuKien(inputs.getLopDuKien())
                .moTa(inputs.getMoTa())
                .trangThaiLopHocPhan(TrangThaiLopHocPhan.DANG_LEN_KE_HOACH)
                .soLuongToiDa(inputs.getSoLuongToiDa())
                .soNhomThucHanh(inputs.getSoNhomThucHanh())
                .build();

        LopHocPhan _lopHocPhanRes = lopHocPhanRepository.saveAndFlush(_lopHocPhan);

        return _lopHocPhanRes;
    }

    public LopHocPhan suaLopHocPhan(ThemLopHocPhanInputs inputs, Long id) {
        LopHocPhan _lopHocPhan = lopHocPhanRepository.getById(id);

        if (_lopHocPhan == null) {
            throw new LopHocPhanIsNotExist();
        }

        HocPhan _hocPhan = hocPhanRepository.getById(inputs.getHocPhanId());

        if (_hocPhan == null) {
            throw new HocPhanIsNotExist();
        }

        HocKyNormal _hocKyNormal = hocKyNormalRepository.getById(inputs.getHocKyNormalId());

        if (_hocKyNormal == null) {
            throw new HocKyNormalIsNotExist();
        }

        _lopHocPhan.setHocPhan(_hocPhan);
        _lopHocPhan.setHocKyNormal(_hocKyNormal);
        _lopHocPhan.setMaLopHocPhan(inputs.getMaLopHocPhan());
        _lopHocPhan.setLopDuKien(inputs.getLopDuKien());
        _lopHocPhan.setMoTa(inputs.getMoTa());
        _lopHocPhan.setSoNhomThucHanh(inputs.getSoNhomThucHanh());
        _lopHocPhan.setSoLuongToiDa(inputs.getSoLuongToiDa());
        _lopHocPhan.setTrangThaiLopHocPhan(inputs.getTrangThaiLopHocPhan());

        LopHocPhan _lopHocPhanRes = lopHocPhanRepository.saveAndFlush(_lopHocPhan);

        return _lopHocPhanRes;
    }

    public List<LopHocPhan> getLopHocPhanWithId(String id) {
        List<LopHocPhan> _listLopHocPhan = lopHocPhanRepository.getListLopHocPhanWithFilter(id);
        return _listLopHocPhan;
    }

    ;

    public List<LopHocPhan> getLopHocPhanDaDangKy(Long hocKyId, Account account) {
        SinhVien _sinhVien = account.getSinhVien();
        List<LopHocPhan> _listLopHocPhan = lopHocPhanRepository.getListLopHocPhanDangKyByHocKyAndSinhVien(_sinhVien.getId(), hocKyId);

        return _listLopHocPhan;
    }

}
