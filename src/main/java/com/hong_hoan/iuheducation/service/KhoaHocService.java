package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.Khoa;
import com.hong_hoan.iuheducation.repository.KhoaRepository;
import com.hong_hoan.iuheducation.resolvers.input.khoa_hoc.ThemKhoaHocInputs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KhoaHocService {
    @Autowired
    private KhoaRepository khoaRepository;

    public Khoa createKhoaHoc(ThemKhoaHocInputs inputs) {
        Khoa _khoaInputs = Khoa.builder()
                .khoa(inputs.getKhoa())
                .moTa(inputs.getMoTa())
                .build();

        Khoa _khoaRes = khoaRepository.saveAndFlush(_khoaInputs);

        return _khoaRes;
    }

}
