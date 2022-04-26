package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.Khoa;
import com.hong_hoan.iuheducation.entity.KhoaVien;
import com.hong_hoan.iuheducation.exception.KhoaVienIsNotExistException;
import com.hong_hoan.iuheducation.repository.KhoaVienRepository;
import com.hong_hoan.iuheducation.resolvers.input.khoa_vien.FindKhoaVienInputs;
import com.hong_hoan.iuheducation.resolvers.input.khoa_vien.ThemKhoaVienInputs;
import com.hong_hoan.iuheducation.resolvers.response.khoa_vien.PaginationKhoaVien;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KhoaVienService {
    @Autowired
    private KhoaVienRepository khoaVienRepository;

    public KhoaVien themKhoaVien(ThemKhoaVienInputs inputs) {
        KhoaVien _khoaVien = KhoaVien.builder()
                .ten(inputs.getTen())
                .link(inputs.getLink())
                .moTa(inputs.getMoTa())
                .build();
        KhoaVien _khoaVienResponse = khoaVienRepository.saveAndFlush(_khoaVien);
        return _khoaVienResponse;
    }

    public List<KhoaVien> xoaKhoaViens(Set<Long> ids) {
        List<KhoaVien> _khoaViens = khoaVienRepository.findAllById(ids);
        if (_khoaViens.isEmpty()) throw new KhoaVienIsNotExistException();
        List<Long> _ids = _khoaViens.stream().map(i -> i.getId()).collect(Collectors.toList());
        khoaVienRepository.deleteAllById(_ids);
        return _khoaViens;
    }

    public List<KhoaVien> findListKhoaVienById(String id) {
        try {
            long _id = Long.valueOf(id);
            KhoaVien _khoaVien = khoaVienRepository.findById(_id).get();
            return Arrays.asList(_khoaVien);
        } catch (NumberFormatException ex) {
            return Arrays.asList();
        }
    }

    public KhoaVien suaKhoaVien(ThemKhoaVienInputs inputs, Long id) {
        KhoaVien _khoaVien = khoaVienRepository.getById(id);

        if (_khoaVien == null) {
            throw new KhoaVienIsNotExistException();
        }

        _khoaVien.setTen(inputs.getTen());
        _khoaVien.setMoTa(inputs.getMoTa());
        _khoaVien.setLink(inputs.getLink());

        KhoaVien _khoaVienRes = khoaVienRepository.saveAndFlush(_khoaVien);

        return _khoaVienRes;
    }

    public PaginationKhoaVien getListKhoaVienPagination(FindKhoaVienInputs inputs) {
        boolean _isInputsEmpty = ObjectUtils.isEmpty(inputs);

        if (_isInputsEmpty) {
            List<KhoaVien> _listKhoaVien = khoaVienRepository.findAll();

            return PaginationKhoaVien.builder().count(_listKhoaVien.size()).data(_listKhoaVien).build();
        }

        String _tenInputs = inputs.getTen() == null ? null : "%" + inputs.getTen() + "%";

        if (inputs.checkPaginationEmpty()) {
            List<KhoaVien> _listKhoaVien = khoaVienRepository.findKhoaVienFilter(inputs.getId(), _tenInputs);
            return PaginationKhoaVien.builder().count(_listKhoaVien.size()).data(_listKhoaVien).build();
        }

        Pageable _pageable = PageRequest.of(inputs.getPage(), inputs.getSize());

        Page<KhoaVien> _khoaVienPage = khoaVienRepository.findKhoaVienFilter(inputs.getId(), _tenInputs, _pageable);

        return PaginationKhoaVien.builder().count(_khoaVienPage.getNumberOfElements()).data(_khoaVienPage.getContent()).build();
    }

}
