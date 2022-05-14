package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.Account;
import com.hong_hoan.iuheducation.entity.HocKyNormal;
import com.hong_hoan.iuheducation.entity.NamHoc;
import com.hong_hoan.iuheducation.exception.NamHocIsNotExist;
import com.hong_hoan.iuheducation.repository.HocKyNormalRepository;
import com.hong_hoan.iuheducation.repository.NamHocRepository;
import com.hong_hoan.iuheducation.resolvers.input.hoc_ky_normal.ThemHocKyNormalInputs;
import com.hong_hoan.iuheducation.resolvers.response.hoc_ky_normal.HocKyNormalResponse;
import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HocKyNormalService {
    @Autowired
    private HocKyNormalRepository hocKyNormalRepository;
    @Autowired
    private NamHocRepository namHocRepository;

    public HocKyNormal themHocKyNormal(ThemHocKyNormalInputs inputs) {

        Optional<NamHoc> _optionalNamHoc = namHocRepository.findById(inputs.getNamHocId());

        if(_optionalNamHoc.isEmpty()) {
            throw new NamHocIsNotExist();
        }

        NamHoc _namHoc = _optionalNamHoc.get();

        HocKyNormal _hocKyNormal = HocKyNormal.builder()
                .thuTuHocKy(inputs.getThuTuHocKy())
                .ghiChu(inputs.getGhiChu())
                .namHoc(_namHoc)
                .build();

        HocKyNormal _hocKyNormalRes = hocKyNormalRepository.saveAndFlush(_hocKyNormal);

        return _hocKyNormalRes;


    }

    public List<HocKyNormal> getListHocKyNormalOfSinhVien(Account account) {
        Date _ngayVaoTruong = account.getSinhVien().getNgayVaoTruong();

        List<HocKyNormal> _listHocKyNormal = hocKyNormalRepository.getListHocKyOfSinhVien(_ngayVaoTruong);

        return _listHocKyNormal;
    }
}
