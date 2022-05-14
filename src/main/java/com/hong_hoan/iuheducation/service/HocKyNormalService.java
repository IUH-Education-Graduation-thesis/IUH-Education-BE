package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.Account;
import com.hong_hoan.iuheducation.entity.HocKyNormal;
import com.hong_hoan.iuheducation.entity.NamHoc;
import com.hong_hoan.iuheducation.exception.HocKyNormalIsNotExist;
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
import java.util.stream.Collectors;

@Service
public class HocKyNormalService {
    @Autowired
    private HocKyNormalRepository hocKyNormalRepository;
    @Autowired
    private NamHocRepository namHocRepository;

    public List<HocKyNormal> xoaHocKys(List<Long> ids) {
        List<HocKyNormal> _listHocKyNormal = hocKyNormalRepository.findAllById(ids);

        if(_listHocKyNormal.size() <= 0) {
            throw new HocKyNormalIsNotExist();
        }

        List<Long> _ids = _listHocKyNormal.stream().map(i -> i.getId()).collect(Collectors.toList());

        hocKyNormalRepository.xoaHocKyNormals(_ids);

        return _listHocKyNormal;
    }

    public HocKyNormal suaHocKyNormal(ThemHocKyNormalInputs inputs, Long id) {

        Optional<HocKyNormal> _hocKyNormalOptional = hocKyNormalRepository.findById(id);

        if(_hocKyNormalOptional.isEmpty()) {
            throw new HocKyNormalIsNotExist();
        }

        Optional<NamHoc> _namHocOptional = namHocRepository.findById(inputs.getNamHocId());

        if(_namHocOptional.isEmpty()) {
            throw new NamHocIsNotExist();
        }

        HocKyNormal _hocKyNormal = _hocKyNormalOptional.get();
        NamHoc _namHoc = _namHocOptional.get();

        _hocKyNormal.setNamHoc(_namHoc);
        _hocKyNormal.setThuTuHocKy(inputs.getThuTuHocKy());
        _hocKyNormal.setGhiChu(inputs.getGhiChu());

        HocKyNormal _hocKyNormalRes = hocKyNormalRepository.saveAndFlush(_hocKyNormal);

        return _hocKyNormalRes;
    }

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
