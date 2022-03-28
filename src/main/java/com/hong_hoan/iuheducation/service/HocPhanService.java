package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.HocPhan;
import com.hong_hoan.iuheducation.repository.HocPhanRepository;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.FindHocPhanInputs;
import com.hong_hoan.iuheducation.resolvers.response.hoc_phan.PaginationHocPhan;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class HocPhanService {
    @Autowired
    private HocPhanRepository hocPhanRepository;

    public PaginationHocPhan findHocPhanWithPagination(FindHocPhanInputs inputs) {
        boolean _isInputsEmpty = ObjectUtils.isEmpty(inputs);

        if (_isInputsEmpty) {
            List<HocPhan> _listHocPhan = hocPhanRepository.findAll();

            return PaginationHocPhan.builder()
                    .count(_listHocPhan.size())
                    .data(_listHocPhan)
                    .build();
        }

        if(inputs.checkAllDataIsNull()) {
            List<HocPhan> _listHocPhan = hocPhanRepository.findAll();

            return PaginationHocPhan.builder()
                    .count(_listHocPhan.size())
                    .data(_listHocPhan)
                    .build();
        }

        if (inputs.checkPaginationIsNull()) {
            List<HocPhan> _listHocPhan = hocPhanRepository.filterHocPhan(inputs.getId(), inputs.getMaHocPhan(), inputs.getNamHocIds(), inputs.getHocKyIds(), inputs.getMonHocIds(), inputs.getKhoaVienIds());
            return PaginationHocPhan.builder()
                    .count(_listHocPhan.size())
                    .data(_listHocPhan)
                    .build();
        }

        Pageable _pageable = PageRequest.of(inputs.getPage(), inputs.getSize());

        Page<HocPhan> _hocPhanPage = hocPhanRepository.filterHocPhan(inputs.getId(), inputs.getMaHocPhan(), inputs.getNamHocIds(), inputs.getHocKyIds(), inputs.getMonHocIds(), inputs.getKhoaVienIds(), _pageable);

        return PaginationHocPhan.builder()
                .count(_hocPhanPage.getNumberOfElements())
                .data(_hocPhanPage.getContent())
                .build();

    }

}
