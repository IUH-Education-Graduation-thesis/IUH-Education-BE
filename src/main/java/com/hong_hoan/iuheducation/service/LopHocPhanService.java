package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.*;
import com.hong_hoan.iuheducation.exception.HocKyNormalIsNotExist;
import com.hong_hoan.iuheducation.exception.HocPhanIsNotExist;
import com.hong_hoan.iuheducation.exception.LopHocPhanIsNotExist;
import com.hong_hoan.iuheducation.repository.HocKyNormalRepository;
import com.hong_hoan.iuheducation.repository.HocPhanRepository;
import com.hong_hoan.iuheducation.repository.LopHocPhanRepository;
import com.hong_hoan.iuheducation.resolvers.input.lop_hoc_phan.ThemLopHocPhanInputs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LopHocPhanService {
    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;
    @Autowired
    private HocPhanRepository hocPhanRepository;
    @Autowired
    private HocKyNormalRepository hocKyNormalRepository;

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

        if(_lopHocPhan == null) {
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
    };

    public List<LopHocPhan> getLopHocPhanDaDangKy(Long hocKyId, Account account) {
        SinhVien _sinhVien = account.getSinhVien();
        List<LopHocPhan> _listLopHocPhan = lopHocPhanRepository.getListLopHocPhanDangKyByHocKyAndSinhVien(_sinhVien.getId(), hocKyId);

        return _listLopHocPhan;
    }

}
