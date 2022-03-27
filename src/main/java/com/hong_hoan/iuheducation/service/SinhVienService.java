package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.SinhVien;
import com.hong_hoan.iuheducation.repository.SinhVienRepository;
import com.hong_hoan.iuheducation.resolvers.input.sinh_vien.FindSinhVienInputs;
import com.hong_hoan.iuheducation.resolvers.response.sinh_vien.PaginationSinhVien;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SinhVienService {
    @Autowired
    private SinhVienRepository sinhVienRepository;

    public PaginationSinhVien findSinhVienWithFilter(FindSinhVienInputs inputs) {
        boolean _isInputsEmpty = ObjectUtils.isEmpty(inputs);

        if (_isInputsEmpty) {
            List<SinhVien> _listSinhVien = sinhVienRepository.findAll();

            return PaginationSinhVien.builder()
                    .count(_listSinhVien.size())
                    .data(_listSinhVien)
                    .build();
        }

        String _maSinhVienInputs = inputs.getMaSinhVien() == null ? null : "%" + inputs.getMaSinhVien() + "%";
        String _tenSinhVienInputs = inputs.getTenSinhVien() == null ? null : "%" + inputs.getTenSinhVien() + "%";

        if (inputs.checkPaginationEmpty()) {
            List<SinhVien> _listSinhVien = sinhVienRepository.findListSinhVienFilter(inputs.getId(), _maSinhVienInputs, _tenSinhVienInputs, inputs.getLopIds(), inputs.getChuyenNganhIds(), inputs.getKhoaVienIds(), inputs.getKhoaHocIds());

            return PaginationSinhVien.builder()
                    .count(_listSinhVien.size())
                    .data(_listSinhVien)
                    .build();
        }

        Pageable _pageable = PageRequest.of(inputs.getPage(), inputs.getSize());

        Page<SinhVien> _sinhVienPage = sinhVienRepository.findListSinhVienFilter(inputs.getId(), _maSinhVienInputs, _tenSinhVienInputs, inputs.getLopIds(), inputs.getChuyenNganhIds(), inputs.getKhoaVienIds(), inputs.getKhoaHocIds(), _pageable);

        return PaginationSinhVien.builder()
                .count(_sinhVienPage.getNumberOfElements())
                .data(_sinhVienPage.getContent())
                .build();
    }
}
