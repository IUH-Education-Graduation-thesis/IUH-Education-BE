package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.GiangVien;
import com.hong_hoan.iuheducation.repository.GiangVienRepository;
import com.hong_hoan.iuheducation.resolvers.input.giang_vien.FindGiangVienInputs;
import com.hong_hoan.iuheducation.resolvers.response.giang_vien.PaginationGiangVien;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GiangVienService {
    @Autowired
    private GiangVienRepository giangVienRepository;

    public List<GiangVien> findGiangVienById(String id) {
        try {
            long _id = Long.valueOf(id);
            try {
                GiangVien _giangVien = giangVienRepository.findById(_id).get();
                return Arrays.asList(_giangVien);
            } catch (NoSuchElementException ex) {
                return Arrays.asList();
            }

        } catch (NumberFormatException ex) {
            return Arrays.asList();
        }
    }

    public PaginationGiangVien findGiangVienPagination(FindGiangVienInputs inputs) {
        if (ObjectUtils.isEmpty(inputs)) {
            List<GiangVien> _listGiangVien = giangVienRepository.findAll();

            return PaginationGiangVien.builder()
                    .count(_listGiangVien.size())
                    .data(_listGiangVien)
                    .build();
        }

        Pageable _pageable = PageRequest.of(inputs.getPage(), inputs.getSize());

        if (inputs.checkBaseInputsEmpty()) {
            Page<GiangVien> _pageGiangVien = giangVienRepository.findAll(_pageable);

            return PaginationGiangVien.builder()
                    .count(_pageGiangVien.getNumberOfElements())
                    .data(_pageGiangVien.getContent())
                    .build();
        }

        try {
            boolean _isIdNull = inputs.getId().isEmpty();
            List<GiangVien> _listGiangVien = findGiangVienById(inputs.getId());

            return PaginationGiangVien.builder()
                    .data(_listGiangVien)
                    .count(_listGiangVien.size())
                    .build();

        } catch (NullPointerException ex) {
            String _nameSearch = inputs.getHoVaTen() == null ? null : "%"+inputs.getHoVaTen()+"%";
            Page<GiangVien> _giangVienPage = giangVienRepository.getGiangVienByNameAndKhoaVienId(inputs.getKhoaVienIds(), _nameSearch, _pageable);

            return PaginationGiangVien.builder()
                    .count(_giangVienPage.getNumberOfElements())
                    .data(_giangVienPage.getContent())
                    .build();
        }
    }

}
