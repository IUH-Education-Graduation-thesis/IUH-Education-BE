package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.KhoaVien;
import com.hong_hoan.iuheducation.repository.KhoaVienRepository;
import com.hong_hoan.iuheducation.resolvers.input.khoa_vien.FindKhoaVienInputs;
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

@Service
@RequiredArgsConstructor
public class KhoaVienService {
    @Autowired
    private KhoaVienRepository khoaVienRepository;

    public List<KhoaVien> findListKhoaVienById(String id) {
        try {
            long _id = Long.valueOf(id);
            KhoaVien _khoaVien = khoaVienRepository.findById(_id).get();
            return Arrays.asList(_khoaVien);
        } catch (NumberFormatException ex) {
            return Arrays.asList();
        }
    }

    public PaginationKhoaVien getListKhoaVienPagination(FindKhoaVienInputs inputs) {
        boolean _isInputsEmpty = ObjectUtils.isEmpty(inputs);

        if(_isInputsEmpty) {
            List<KhoaVien> _listKhoaVien = khoaVienRepository.findAll();

            return PaginationKhoaVien.builder()
                    .count(_listKhoaVien.size())
                    .data(_listKhoaVien)
                    .build();
        }

        Pageable _pageable = PageRequest.of(inputs.getPage(), inputs.getSize());

        if(inputs.checkBaseDateEmpty()) {
            Page<KhoaVien> _pageKhoaVien = khoaVienRepository.findAll(_pageable);

            return PaginationKhoaVien.builder()
                    .count(_pageKhoaVien.getNumberOfElements())
                    .data(_pageKhoaVien.getContent())
                    .build();
        }

        try {
            boolean _isIdInputsNull = inputs.getId().isEmpty();
            List<KhoaVien> _listKhoaVien = findListKhoaVienById(inputs.getId());

            return PaginationKhoaVien.builder()
                    .count(_listKhoaVien.size())
                    .data(_listKhoaVien)
                    .build();
        } catch (NullPointerException ex) {
            Page<KhoaVien> _pageKhoaVien = khoaVienRepository.findByTenContaining(inputs.getTen(), _pageable);

            return PaginationKhoaVien.builder()
                    .count(_pageKhoaVien.getNumberOfElements())
                    .data(_pageKhoaVien.getContent())
                    .build();
        }
    }

}
