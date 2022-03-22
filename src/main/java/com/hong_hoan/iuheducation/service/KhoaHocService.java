package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.Khoa;
import com.hong_hoan.iuheducation.exception.KhoaHocIsNotExist;
import com.hong_hoan.iuheducation.repository.KhoaRepository;
import com.hong_hoan.iuheducation.resolvers.input.khoa_hoc.FindKhoaHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.khoa_hoc.ThemKhoaHocInputs;
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

@Service
@RequiredArgsConstructor
public class KhoaHocService {
    @Autowired
    private KhoaRepository khoaRepository;

    public PaginationKhoaHoc findKhoaHocs(FindKhoaHocInputs inputs) {
        boolean _isEmptyInputs = ObjectUtils.isEmpty(inputs);

        if(_isEmptyInputs) {
            Pageable _pageable = PageRequest.of(0, 10);
            Page<Khoa> _pageKhoa = khoaRepository.findAll(_pageable);

            return PaginationKhoaHoc.builder()
                    .count(_pageKhoa.getNumberOfElements())
                    .data(_pageKhoa.getContent())
                    .build();
        }

        Pageable _pageable = PageRequest.of(inputs.getPage(), inputs.getSize());

        if(inputs.checkBaseValuesEmpty()) {
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

    public void deleteKhoaHoc(String id) throws NumberFormatException {
        long _idLong = Long.valueOf(id);
        boolean _isExistKhoaHoc = khoaRepository.existsById(_idLong);

        if(!_isExistKhoaHoc) {
            throw new KhoaHocIsNotExist();
        }

        khoaRepository.deleteById(_idLong);
    }

    public Khoa createKhoaHoc(ThemKhoaHocInputs inputs) {
        Khoa _khoaInputs = Khoa.builder()
                .khoa(inputs.getKhoa())
                .moTa(inputs.getMoTa())
                .build();

        Khoa _khoaRes = khoaRepository.saveAndFlush(_khoaInputs);

        return _khoaRes;
    }

}
