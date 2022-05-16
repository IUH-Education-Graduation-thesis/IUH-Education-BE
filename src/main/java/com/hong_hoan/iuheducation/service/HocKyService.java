package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.HocKy;
import com.hong_hoan.iuheducation.entity.HocPhan;
import com.hong_hoan.iuheducation.entity.Khoa;
import com.hong_hoan.iuheducation.entity.LopHocPhan;
import com.hong_hoan.iuheducation.exception.HocKyIsNotExist;
import com.hong_hoan.iuheducation.exception.HocPhanIsNotExist;
import com.hong_hoan.iuheducation.exception.KhoaHocIsNotExist;
import com.hong_hoan.iuheducation.exception.NamHocIsNotExist;
import com.hong_hoan.iuheducation.repository.HocKyRepository;
import com.hong_hoan.iuheducation.repository.HocPhanRepository;
import com.hong_hoan.iuheducation.repository.KhoaRepository;
import com.hong_hoan.iuheducation.resolvers.input.hoc_ky.ThemHocKyInputs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class HocKyService {
    @Autowired
    private HocKyRepository hocKyRepository;
    @Autowired
    private KhoaRepository khoaRepository;
    @Autowired
    private HocPhanRepository hocPhanRepository;

    public HocKy themHocPhanVaoHocKy(List<Long> hocPhanIds, Long hocKyId) {
        Optional<HocKy> _hocKyOption = hocKyRepository.findById(hocKyId);

        if (_hocKyOption.isEmpty()) {
            throw new HocKyIsNotExist();
        }

        HocKy _hocKy = _hocKyOption.get();
        List<HocPhan> _listHocPhan = hocPhanRepository.findAllById(hocPhanIds);

        if (_listHocPhan.isEmpty()) {
            throw new HocPhanIsNotExist();
        }

        List<HocPhan> _listHocPhanOfHocKy = _hocKy.getHocPhans().stream().collect(Collectors.toList());

        List<HocPhan> _listHocPhanConcat = Stream.concat(_listHocPhanOfHocKy.stream(), _listHocPhan.stream())
                .collect(Collectors.toList());

        _hocKy.setHocPhans(new HashSet<>(_listHocPhanConcat));

        HocKy _hocKyRes = hocKyRepository.saveAndFlush(_hocKy);

        return _hocKyRes;
    }

    public List<HocKy> xoaHocKys(List<Long> ids) {
        List<HocKy> _hocKies = hocKyRepository.findAllById(ids);

        if (_hocKies.size() <= 0) {
            throw new HocKyIsNotExist();
        }

        List<Long> _ids = _hocKies.stream().map(i -> i.getId()).collect(Collectors.toList());

        hocKyRepository.xoaHocKies(_ids);

        return _hocKies;
    }

    public HocKy suaHocKy(ThemHocKyInputs inputs, Long id) {
        HocKy _hocKy = hocKyRepository.getById(id);

        if (_hocKy == null) {
            throw new HocKyIsNotExist();
        }

        Khoa _khoa = khoaRepository.getById(inputs.getKhoaId());

        if (_khoa == null) {
            throw new KhoaHocIsNotExist();
        }

        _hocKy.setKhoa(_khoa);
        _hocKy.setThuTu(inputs.getThuTu());
        _hocKy.setMoTa(inputs.getMoTa());

        HocKy _hocKyRes = hocKyRepository.saveAndFlush(_hocKy);

        return _hocKyRes;
    }

    public HocKy themHocKy(ThemHocKyInputs inputs) {
        Khoa _khoa = khoaRepository.getById(inputs.getKhoaId());

        if (_khoa == null) {
            throw new KhoaHocIsNotExist();
        }

        HocKy _hocKy = HocKy.builder()
                .moTa(inputs.getMoTa())
                .thuTu(inputs.getThuTu())
                .khoa(_khoa)
                .build();

        HocKy _hocKyRes = hocKyRepository.saveAndFlush(_hocKy);

        return _hocKyRes;
    }
}
