package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.Lop;
import com.hong_hoan.iuheducation.repository.LopRepository;
import com.hong_hoan.iuheducation.resolvers.input.lop.FindLopHocInputs;
import com.hong_hoan.iuheducation.resolvers.response.lop.PaginationLopHoc;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LopService {
    @Autowired
    private LopRepository lopRepository;

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