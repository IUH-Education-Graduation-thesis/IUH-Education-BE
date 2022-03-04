package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.NamHoc;
import com.hong_hoan.iuheducation.repository.NamHocRepository;
import com.hong_hoan.iuheducation.resolvers.input.nam_hoc.ThemNamHocInputs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NamHocService {
    @Autowired
    private NamHocRepository namHocRepository;

    public NamHoc themNamHoc(ThemNamHocInputs inputs) {
        NamHoc _namHoc = NamHoc.builder()
                .moTa(inputs.getMoTa())
                .ngayBatDau(inputs.getNgayBatDau())
                .ngayKetThuc(inputs.getNgayKetThuc())
                .build();

        NamHoc _namHocResponse = namHocRepository.saveAndFlush(_namHoc);

        return _namHocResponse;
    }
}
