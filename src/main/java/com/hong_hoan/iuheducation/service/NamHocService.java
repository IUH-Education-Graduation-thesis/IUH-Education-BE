package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.NamHoc;
import com.hong_hoan.iuheducation.repository.NamHocRepository;
import com.hong_hoan.iuheducation.resolvers.input.nam_hoc.FilterNamHocInputs;
import com.hong_hoan.iuheducation.resolvers.response.nam_hoc.PaginationNamHoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

@Service
public class NamHocService {
    @Autowired
    private NamHocRepository namHocRepository;

    public List<NamHoc> getAllNamHoc() {
        return namHocRepository.findAll();
    }

    public PaginationNamHoc filterNamHoc(FilterNamHocInputs inputs) {
        if(ObjectUtils.isEmpty(inputs) || inputs.isAllFieldEmpty()) {
            List<NamHoc> _listNamHocs = namHocRepository.findAll();

            return PaginationNamHoc.builder()
                    .count(_listNamHocs.size())
                    .result(_listNamHocs)
                    .build();
        }

        if(inputs.isPaginationFieldEmpty()) {
            List<NamHoc> _listNamHoc = namHocRepository.filterNamHoc(inputs.getId(), inputs.getFromYear(), inputs.getToYear());

            return PaginationNamHoc.builder()
                    .count(_listNamHoc.size())
                    .result(_listNamHoc)
                    .build();
        }

        Pageable _pageable = PageRequest.of(inputs.getPage(), inputs.getSize());

        Page<NamHoc> _pageNamHoc = namHocRepository.filterNamHoc(inputs.getId(), inputs.getFromYear(), inputs.getToYear(), _pageable);

        return PaginationNamHoc.builder()
                .count(_pageNamHoc.getNumberOfElements())
                .result(_pageNamHoc.getContent())
                .build();
    }
}
