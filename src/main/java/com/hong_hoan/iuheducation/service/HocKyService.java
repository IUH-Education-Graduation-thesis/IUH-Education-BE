package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.HocKy;
import com.hong_hoan.iuheducation.exception.HocKyIsNotExist;
import com.hong_hoan.iuheducation.exception.NamHocIsNotExist;
import com.hong_hoan.iuheducation.repository.HocKyRepository;
import com.hong_hoan.iuheducation.resolvers.input.hoc_ky.ThemHocKyInputs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HocKyService {
    @Autowired
    private HocKyRepository hocKyRepository;

    public void xoaHocKy(String id) throws NumberFormatException {
        long _id = Long.valueOf(id);

        boolean _isHocKyExist = hocKyRepository.existsById(_id);

        if(!_isHocKyExist) throw new HocKyIsNotExist();

        hocKyRepository.deleteById(_id);
    }

    public HocKy themHocKy(ThemHocKyInputs inputs) throws NumberFormatException {
        long _namHocIdLong = Long.valueOf(inputs.getNamHocId());

        HocKy _hocKy = HocKy.builder()
                .moTa(inputs.getMoTa())
                .thuTu(inputs.getThuTu())
                .build();

        HocKy _hocKyRes = hocKyRepository.saveAndFlush(_hocKy);

        return _hocKyRes;
    }
}
