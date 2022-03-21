package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.NamHoc;
import com.hong_hoan.iuheducation.exception.InputsEmptyException;
import com.hong_hoan.iuheducation.exception.NamHocIsNotExist;
import com.hong_hoan.iuheducation.exception.NgayBatDauSauNgayKetThucException;
import com.hong_hoan.iuheducation.repository.NamHocRepository;
import com.hong_hoan.iuheducation.resolvers.input.nam_hoc.FindNamHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.nam_hoc.ThemNamHocInputs;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NamHocService {
    @Autowired
    private NamHocRepository namHocRepository;

    public NamHoc findNamHocById(String id) {
        try {
            long _id = Long.valueOf(id);
            NamHoc _namHocRes = namHocRepository.findById(_id).get();
            return _namHocRes;
        } catch (NumberFormatException ex) {
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<NamHoc> findNamHocs(FindNamHocInputs inputs) {

        boolean _inputsEmpty = ObjectUtils.isEmpty(inputs);

        if (_inputsEmpty) {
            return namHocRepository.findAll();
        }

        try {
            boolean _idIsEmpty = inputs.getId().isEmpty();
            NamHoc _namHoc = findNamHocById(inputs.getId());
            if (_namHoc == null) {
                return Arrays.asList();
            }

            return Arrays.asList(_namHoc);
        } catch (NullPointerException ex) {
            List<NamHoc> _listNamHoc = namHocRepository.findByNgayBatDauBetweenAndNgayKetThucBetween(inputs.getFormDate(), inputs.getToDate(), inputs.getFormDate(), inputs.getToDate());

            System.out.println(_listNamHoc.size());
            return _listNamHoc;
        }
    }

    public void xoaNamHocById(String id) throws NumberFormatException {
        long _id = Long.valueOf(id);

        boolean _isExistNamHoc = namHocRepository.existsById(_id);

        if (!_isExistNamHoc) {
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
