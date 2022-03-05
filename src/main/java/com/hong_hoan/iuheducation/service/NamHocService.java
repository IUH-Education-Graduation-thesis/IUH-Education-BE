package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.NamHoc;
import com.hong_hoan.iuheducation.exception.NamHocIsNotExist;
import com.hong_hoan.iuheducation.exception.NgayBatDauSauNgayKetThucException;
import com.hong_hoan.iuheducation.repository.NamHocRepository;
import com.hong_hoan.iuheducation.resolvers.input.nam_hoc.ThemNamHocInputs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NamHocService {
    @Autowired
    private NamHocRepository namHocRepository;

    public List<NamHoc> findNamHoc(String id) {

        List<NamHoc> _listNamHoc = namHocRepository.findByIdEquals(id);

        return _listNamHoc;
    }

    public void xoaNamHocById(String id) throws NumberFormatException {
        long _id = Long.valueOf(id);

        boolean _isExistNamHoc = namHocRepository.existsById(_id);

        if(!_isExistNamHoc) {
            throw new NamHocIsNotExist();
        }

        namHocRepository.deleteById(_id);
    }

    public NamHoc themNamHoc(ThemNamHocInputs inputs) {
        if (inputs.getNgayBatDau().after(inputs.getNgayKetThuc())) {
            throw new NgayBatDauSauNgayKetThucException();
        }

        NamHoc _namHoc = NamHoc.builder()
                .moTa(inputs.getMoTa())
                .ngayBatDau(inputs.getNgayBatDau())
                .ngayKetThuc(inputs.getNgayKetThuc())
                .build();

        NamHoc _namHocResponse = namHocRepository.saveAndFlush(_namHoc);

        return _namHocResponse;
    }
}
