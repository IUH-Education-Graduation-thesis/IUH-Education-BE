package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.ChuyenNganh;
import com.hong_hoan.iuheducation.entity.Khoa;
import com.hong_hoan.iuheducation.exception.ChuyenNganhIsNotExistExcepton;
import com.hong_hoan.iuheducation.exception.KhoaHocIsNotExist;
import com.hong_hoan.iuheducation.repository.ChuyenNganhRepository;
import com.hong_hoan.iuheducation.repository.KhoaRepository;
import com.hong_hoan.iuheducation.resolvers.input.khoa_hoc.FindKhoaHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.khoa_hoc.ThemKhoaHocInputs;
import com.hong_hoan.iuheducation.resolvers.response.chuyen_nganh.ChuyenNganhResponse;
import com.hong_hoan.iuheducation.resolvers.response.khoa_hoc.FindKhoaHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.khoa_hoc.PaginationKhoaHoc;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KhoaHocService {
    @Autowired
    private KhoaRepository khoaRepository;
    @Autowired
    private ChuyenNganhRepository chuyenNganhRepository;

    public PaginationKhoaHoc findKhoaHocs(FindKhoaHocInputs inputs) {
        boolean _isEmptyInputs = ObjectUtils.isEmpty(inputs);

        if (_isEmptyInputs) {
            Pageable _pageable = PageRequest.of(0, 10);
            Page<Khoa> _pageKhoa = khoaRepository.findAll(_pageable);

            return PaginationKhoaHoc.builder()
                    .count(_pageKhoa.getNumberOfElements())
                    .data(_pageKhoa.getContent())
                    .build();
        }

        Pageable _pageable = PageRequest.of(inputs.getPage(), inputs.getSize());

        if (inputs.checkBaseValuesEmpty()) {
            Page<Khoa> _pageKhoa = khoaRepository.findAll(_pageable);

            return PaginationKhoaHoc.builder()
                    .count(_pageKhoa.getNumberOfElements())
                    .data(_pageKhoa.getContent())
                    .build();
        }

        try {
            boolean _isNotNullId = !inputs.getId().isEmpty();

            try {
                long _id = Long.valueOf(inputs.getId());
                Page<Khoa> _pageKhoa = khoaRepository.findById(_id, _pageable);

                return PaginationKhoaHoc.builder()
                        .count(_pageKhoa.getNumberOfElements())
                        .data(_pageKhoa.getContent())
                        .build();
            } catch (NumberFormatException ex) {
                return PaginationKhoaHoc.builder()
                        .count(0)
                        .data(Arrays.asList())
                        .build();
            }

        } catch (NullPointerException ex) {
            Page<Khoa> _pageKhoa = khoaRepository.findByKhoa(inputs.getTenKhoaHoc(), _pageable);

            return PaginationKhoaHoc.builder()
                    .count(_pageKhoa.getNumberOfElements())
                    .data(_pageKhoa.getContent())
                    .build();
        }
    }

    public List<Khoa> findAllKhoaHoc() {
        List<Khoa> _khoa = khoaRepository.findAll();

        return _khoa;
    }

    public Khoa findById(String id) throws NumberFormatException {
        long _id = Long.valueOf(id);

        try {
            Khoa _khoa = khoaRepository.findById(_id).get();

            return _khoa;
        } catch (NoSuchElementException ex) {
            throw new KhoaHocIsNotExist();
        }
    }

    public List<Khoa> deleteKhoaHoc(List<Long> ids) {
        List<Khoa> _listKhoa = khoaRepository.findAllById(ids);

        if(_listKhoa.size() <= 0) {
            throw new KhoaHocIsNotExist();
        }

        List<Long> _ids = _listKhoa.stream().map(i -> i.getId()).collect(Collectors.toList());

        khoaRepository.xoaKhoaHocs(_ids);

        return _listKhoa;
    }

    public Khoa suaKhoaHoc(ThemKhoaHocInputs inputs, Long id) {
        Khoa _khoa = khoaRepository.getById(id);

        if (_khoa == null) {
            throw new KhoaHocIsNotExist();
        }

        ChuyenNganh _chuyenNganh = chuyenNganhRepository.getById(inputs.getChuyenNganhId());

        if (_chuyenNganh == null) {
            throw new ChuyenNganhIsNotExistExcepton();
        }

        _khoa.setKhoa(inputs.getKhoa());
        _khoa.setThoiGianBatDau(inputs.getThoiGianBatDau());
        _khoa.setThoiGianKetThuc(inputs.getThoiGianKetThuc());
        _khoa.setMoTa(inputs.getMoTa());

        Khoa _khoaRes = khoaRepository.saveAndFlush(_khoa);

        return _khoaRes;
    }

    public Khoa createKhoaHoc(ThemKhoaHocInputs inputs) {
        ChuyenNganh _chuyenNganh = chuyenNganhRepository.getById(inputs.getChuyenNganhId());

        if (_chuyenNganh == null) {
            throw new ChuyenNganhIsNotExistExcepton();
        }

        Khoa _khoaInputs = Khoa.builder()
                .khoa(inputs.getKhoa())
                .moTa(inputs.getMoTa())
                .thoiGianBatDau(inputs.getThoiGianBatDau())
                .thoiGianKetThuc(inputs.getThoiGianKetThuc())
                .chuyenNganh(_chuyenNganh)
                .build();

        Khoa _khoaRes = khoaRepository.saveAndFlush(_khoaInputs);

        return _khoaRes;
    }

}
