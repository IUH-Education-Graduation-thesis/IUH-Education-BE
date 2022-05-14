package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.NamHoc;
import com.hong_hoan.iuheducation.exception.NamHocIsNotExist;
import com.hong_hoan.iuheducation.repository.NamHocRepository;
import com.hong_hoan.iuheducation.resolvers.input.nam_hoc.FilterNamHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.nam_hoc.ThemNamHocInputs;
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

    public List<NamHoc> xoaNamHoc(List<Long> ids) {
        List<NamHoc> _listNamHocFind = namHocRepository.findAllById(ids);

        if(_listNamHocFind.size() <= 0) {
            throw new NamHocIsNotExist();
        }

        namHocRepository.deleteAll(_listNamHocFind);

        return _listNamHocFind;
    }

    public NamHoc suaNamHoc(ThemNamHocInputs inputs, Long id) {
        NamHoc _namHoc = namHocRepository.getById(id);

        if(_namHoc == null) {
            throw new NamHocIsNotExist();
        }

        _namHoc.setNamBatDau(inputs.getNamBatDau());
        _namHoc.setNamKetThuc(inputs.getNamKetThuc());
        _namHoc.setGhiChu(inputs.getGhiChu());

        NamHoc _namHocRes = namHocRepository.saveAndFlush(_namHoc);

        return _namHocRes;
    }

    public NamHoc themNamHoc(ThemNamHocInputs inputs) {
        NamHoc _namHoc = NamHoc.builder()
                .namBatDau(inputs.getNamBatDau())
                .namKetThuc(inputs.getNamKetThuc())
                .ghiChu(inputs.getGhiChu())
                .build();

        NamHoc _namHocRes = namHocRepository.saveAndFlush(_namHoc);
        return _namHocRes;
    }

    public PaginationNamHoc filterNamHoc(FilterNamHocInputs inputs) {
        if (ObjectUtils.isEmpty(inputs) || inputs.isAllFieldEmpty()) {
            List<NamHoc> _listNamHocs = namHocRepository.findAll();

            return PaginationNamHoc.builder()
                    .count(_listNamHocs.size())
                    .result(_listNamHocs)
                    .build();
        }

        if (inputs.isPaginationFieldEmpty()) {
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
