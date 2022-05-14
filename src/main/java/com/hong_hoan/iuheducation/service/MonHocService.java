package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.ChuyenNganh;
import com.hong_hoan.iuheducation.entity.GiangVien;
import com.hong_hoan.iuheducation.entity.KhoaVien;
import com.hong_hoan.iuheducation.entity.MonHoc;
import com.hong_hoan.iuheducation.exception.ChuyenNganhIsNotExistExcepton;
import com.hong_hoan.iuheducation.exception.GiangVienIsNotExistException;
import com.hong_hoan.iuheducation.exception.KhoaVienIsNotExistException;
import com.hong_hoan.iuheducation.exception.MonHocIsExistException;
import com.hong_hoan.iuheducation.repository.GiangVienRepository;
import com.hong_hoan.iuheducation.repository.KhoaVienRepository;
import com.hong_hoan.iuheducation.repository.MonHocRepository;
import com.hong_hoan.iuheducation.resolvers.input.mon_hoc.ThemMonHocInputs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonHocService {
    @Autowired
    private MonHocRepository monHocRepository;
    @Autowired
    private KhoaVienRepository khoaVienRepository;
    @Autowired
    private GiangVienRepository giangVienRepository;

    public List<GiangVien> xoaGiangVienOfMonHoc(List<Long> ids, Long id) {
        List<GiangVien> _listGiangVienFind = giangVienRepository.findAllById(ids);

        if (_listGiangVienFind.size() <= 0) {
            throw new GiangVienIsNotExistException();
        }

        Optional<MonHoc> _monHocFind = monHocRepository.findById(id);

        if (_monHocFind.isEmpty()) {
            throw new MonHocIsExistException();
        }

        MonHoc _monHoc = _monHocFind.get();

        boolean _isRemove = _monHoc.getGiangViens().removeAll(_listGiangVienFind);

        if (!_isRemove) {
            throw new GiangVienIsNotExistException();
        }

        monHocRepository.saveAndFlush(_monHoc);

        return _listGiangVienFind;
    }

    public MonHoc suaMonHoc(ThemMonHocInputs inputs, Long id) {
        MonHoc _monHoc = monHocRepository.getById(id);

        if (_monHoc == null) {
            throw new MonHocIsExistException();
        }

        KhoaVien _khoaVien = khoaVienRepository.getById(inputs.getKhoaVienID());

        if (_khoaVien == null) {
            throw new KhoaVienIsNotExistException();
        }

        List<GiangVien> _giangViens = giangVienRepository.findAllById(inputs.getGiangVienIds());
        Set<GiangVien> _giangViensSet = new HashSet<>(_giangViens);

        _monHoc.setTen(inputs.getTen());
        _monHoc.setMoTa(inputs.getMoTa());
        _monHoc.setKhoaVien(_khoaVien);
        _monHoc.setGiangViens(_giangViensSet);

        MonHoc _monHocRes = monHocRepository.saveAndFlush(_monHoc);

        return _monHocRes;
    }

    public MonHoc themMonHoc(ThemMonHocInputs inputs) {
        boolean _isExistKhoaVien = khoaVienRepository.existsById(inputs.getKhoaVienID());
        if (!_isExistKhoaVien) {
            throw new KhoaVienIsNotExistException();
        }

        Optional<KhoaVien> _khoaVienOptional = khoaVienRepository.findById(inputs.getKhoaVienID());
        try {
            KhoaVien _khoaVien = _khoaVienOptional.get();
            List<GiangVien> _giangVienList = giangVienRepository.findAllById(inputs.getGiangVienIds());
            Set<GiangVien> _giangVienSet = new HashSet<>(_giangVienList);

            MonHoc _monHoc = MonHoc.builder()
                    .ten(inputs.getTen())
                    .moTa(inputs.getMoTa())
                    .khoaVien(_khoaVien)
                    .giangViens(_giangVienSet)
                    .build();
            MonHoc _monHocRes = monHocRepository.saveAndFlush(_monHoc);
            return _monHocRes;
        } catch (NoSuchElementException e) {
            throw new KhoaVienIsNotExistException();
        }
    }

    public List<MonHoc> xoaMonHocs(Set<Long> ids) {
        List<MonHoc> _monHocs = monHocRepository.findAllById(ids);
        if (_monHocs.isEmpty())
            throw new KhoaVienIsNotExistException();
        List<Long> _ids = _monHocs.stream().map(i -> i.getId()).collect(Collectors.toList());
        monHocRepository.xoaMonHocs(_ids);
        return _monHocs;
    }
}
