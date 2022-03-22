package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.NamHoc;
import com.hong_hoan.iuheducation.exception.NamHocIsNotExist;
import com.hong_hoan.iuheducation.exception.NgayBatDauSauNgayKetThucException;
import com.hong_hoan.iuheducation.repository.NamHocRepository;
import com.hong_hoan.iuheducation.resolvers.input.nam_hoc.FindNamHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.nam_hoc.ThemNamHocInputs;
import com.hong_hoan.iuheducation.resolvers.response.nam_hoc.FindNamHocRest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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


    public FindNamHocRest findNamHocs(FindNamHocInputs inputs) {

        boolean _isInputsEmpty = ObjectUtils.isEmpty(inputs);
        if (!_isInputsEmpty) {
            Pageable _pageable = PageRequest.of(inputs.getPage(), inputs.getSize());

            if (inputs.checkIsNull()) {
                Page<NamHoc> _namHocPage = namHocRepository.findAll(_pageable);

                List<NamHoc> _listNamHoc = _namHocPage.getContent();
                long _countNamHoc = _namHocPage.getTotalElements();

                return FindNamHocRest.builder()
                        .count(_countNamHoc)
                        .data(_listNamHoc)
                        .build();
            }

            try {
                boolean _idIsEmpty = inputs.getId().isEmpty();
                long _id = Long.valueOf(inputs.getId());

                Page<NamHoc> _namHoc = namHocRepository.findById(_id, _pageable);

                List<NamHoc> _listNamHoc = _namHoc.getContent();
                long _totalItem = _namHoc.getTotalElements();

                return FindNamHocRest.builder()
                        .count(_totalItem)
                        .data(_listNamHoc)
                        .build();

            } catch (NullPointerException ex) {
                Page<NamHoc> _namHocPage = namHocRepository.findByNgayBatDauBetweenAndNgayKetThucBetween(inputs.getFormDate(), inputs.getToDate(), inputs.getFormDate(), inputs.getToDate(), _pageable);

                List<NamHoc> _listNamHoc = _namHocPage.getContent();
                long _totalItem = _namHocPage.getTotalElements();

                return FindNamHocRest.builder()
                        .count(_totalItem)
                        .data(_listNamHoc)
                        .build();
            }
        }

        FindNamHocInputs _inputs = FindNamHocInputs.builder()
                .page(0)
                .size(10)
                .build();

        Pageable _pageable = PageRequest.of(_inputs.getPage(), _inputs.getSize());

        Page<NamHoc> _namHocPage = namHocRepository.findAll(_pageable);
        List<NamHoc> _listNamHoc = _namHocPage.getContent();
        long _totalItem = _namHocPage.getTotalElements();
        return FindNamHocRest.builder()
                .count(_totalItem)
                .data(_listNamHoc)
                .build();
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
