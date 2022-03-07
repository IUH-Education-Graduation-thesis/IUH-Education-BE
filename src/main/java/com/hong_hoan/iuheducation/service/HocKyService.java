package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.HocKy;
import com.hong_hoan.iuheducation.entity.NamHoc;
import com.hong_hoan.iuheducation.exception.NamHocIsNotExist;
import com.hong_hoan.iuheducation.repository.HocKyRepository;
import com.hong_hoan.iuheducation.repository.NamHocRepository;
import com.hong_hoan.iuheducation.resolvers.input.hoc_ky.ThemHocKyInputs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HocKyService {
    @Autowired
    private HocKyRepository hocKyRepository;
    @Autowired
    private NamHocRepository namHocRepository;

    public HocKy themHocKy(ThemHocKyInputs inputs) throws NumberFormatException {
        long _namHocIdLong = Long.valueOf(inputs.getNamHocId());

        boolean _isExistNamHoc = namHocRepository.existsById(_namHocIdLong);

        if(!_isExistNamHoc) {
            throw new NamHocIsNotExist();
        }

        NamHoc _namHoc = namHocRepository.getById(_namHocIdLong);
        HocKy _hocKy = HocKy.builder()
                .moTa(inputs.getMoTa())
                .thuTu(inputs.getThuTu())
                .namHoc(_namHoc)
                .build();

        HocKy _hocKyRes = hocKyRepository.saveAndFlush(_hocKy);

        return _hocKyRes;
    }
}
