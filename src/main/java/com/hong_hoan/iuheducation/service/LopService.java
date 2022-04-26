package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.Khoa;
import com.hong_hoan.iuheducation.entity.Lop;
import com.hong_hoan.iuheducation.exception.KhoaHocIsNotExist;
import com.hong_hoan.iuheducation.exception.LopIsNotExist;
import com.hong_hoan.iuheducation.repository.KhoaRepository;
import com.hong_hoan.iuheducation.repository.LopRepository;
import com.hong_hoan.iuheducation.resolvers.input.lop.FindLopHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.lop.ThemLopInputs;
import com.hong_hoan.iuheducation.resolvers.response.lop.PaginationLopHoc;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LopService {
    @Autowired
    private LopRepository lopRepository;
    @Autowired
    private KhoaRepository khoaRepository;

    public List<Lop> xoaLops(List<Long> ids) throws LopIsNotExist {
        List<Lop> _listLop = lopRepository.findAllById(ids);

        if(_listLop.size() <= 0) {
            throw new LopIsNotExist();
        }

        List<Long> _ids = _listLop.stream().map(i -> i.getId()).collect(Collectors.toList());

        lopRepository.xoaLops(_ids);

        return _listLop;
    }

    public Lop suaLop(ThemLopInputs inputs, Long id) throws LopIsNotExist {
        Lop _lop = lopRepository.getById(id);

        if(_lop == null) {
            throw new LopIsNotExist();
        }

        Khoa _khoa = khoaRepository.getById(inputs.getKhoaId());

        if(_khoa == null) {
            throw new KhoaHocIsNotExist();
        }

        _lop.setTen(inputs.getTen());
        _lop.setMoTa(inputs.getMoTa());
        _lop.setKhoa(_khoa);

        Lop _dataRes = lopRepository.saveAndFlush(_lop);

        return _dataRes;
    }

    public Lop themLop(ThemLopInputs inputs) {
        Khoa _khoa = khoaRepository.getById(inputs.getKhoaId());

        if(_khoa == null) {
            throw new KhoaHocIsNotExist();
        }

        Lop _lop = Lop.builder()
                .ten(inputs.getTen())
                .moTa(inputs.getMoTa())
                .khoa(_khoa)
                .build();

        return _lop;
    }

    public PaginationLopHoc findLopHocPagination(FindLopHocInputs inputs) {
        boolean _isInputsEmpty = ObjectUtils.isEmpty(inputs);

        if(_isInputsEmpty) {
            List<Lop> _listLopHoc = lopRepository.findAll();

            return PaginationLopHoc.builder()
                    .count(_listLopHoc.size())
                    .data(_listLopHoc)
                    .build();
        }

        Pageable _pageable = PageRequest.of(inputs.getPage(), inputs.getSize());

        if(inputs.checkBaseValueEmpty()) {
            Page<Lop> _pageLop = lopRepository.findAll(_pageable);

            return PaginationLopHoc.builder()
                    .count(_pageLop.getNumberOfElements())
                    .data(_pageLop.getContent())
                    .build();
        }

        String _nameSearch = inputs.getTen() == null ? null : "%"+inputs.getTen()+"%";

        Page<Lop> _pageLop = lopRepository.findLopByFilter(inputs.getId(), _nameSearch, inputs.getKhoaHocIds(), inputs.getChuyenNganhIds(), inputs.getKhoaVienIds(), _pageable);

        return PaginationLopHoc.builder()
                .count(_pageLop.getNumberOfElements())
                .data(_pageLop.getContent())
                .build();
    }

}
