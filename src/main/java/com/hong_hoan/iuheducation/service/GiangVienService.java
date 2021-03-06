package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.ChuyenNganh;
import com.hong_hoan.iuheducation.entity.GiangVien;
import com.hong_hoan.iuheducation.entity.KhoaVien;
import com.hong_hoan.iuheducation.exception.ChuyenNganhIsNotExistExcepton;
import com.hong_hoan.iuheducation.exception.GiangVienIsNotExistException;
import com.hong_hoan.iuheducation.exception.KhoaVienIsNotExistException;
import com.hong_hoan.iuheducation.repository.ChuyenNganhRepository;
import com.hong_hoan.iuheducation.repository.GiangVienRepository;
import com.hong_hoan.iuheducation.resolvers.input.giang_vien.FindGiangVienInputs;
import com.hong_hoan.iuheducation.resolvers.input.giang_vien.ThemGiangVienInputs;
import com.hong_hoan.iuheducation.resolvers.response.giang_vien.GiangVienResponse;
import com.hong_hoan.iuheducation.resolvers.response.giang_vien.PaginationGiangVien;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GiangVienService {
    @Autowired
    private GiangVienRepository giangVienRepository;
    @Autowired
    private ChuyenNganhRepository chuyenNganhRepository;

    public GiangVien suaGiangVien(ThemGiangVienInputs inputs, Long id) {
        GiangVien _giangVien = giangVienRepository.getById(id);

        if (_giangVien == null) {
            throw new GiangVienIsNotExistException();
        }

        ChuyenNganh _chuyenNganh = chuyenNganhRepository.getById(inputs.getChuyenNganhID());

        if (_chuyenNganh == null) {
            throw new ChuyenNganhIsNotExistExcepton();
        }

        _giangVien.setHoTenDem(inputs.getHoTenDem());
        _giangVien.setTen(inputs.getTen());
        _giangVien.setGioiTinh(inputs.isGioiTinh());
        _giangVien.setEmail(inputs.getEmail());
        _giangVien.setSoDienThoai(inputs.getSoDienThoai());
        _giangVien.setChuyenNganh(_chuyenNganh);
        _giangVien.setHocHam(inputs.getHocHam());

        GiangVien _giangVienRes = giangVienRepository.saveAndFlush(_giangVien);

        return _giangVienRes;
    }

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

        if (inputs.checkAllFieldNull()) {
            List<GiangVien> _listGiangVien = giangVienRepository.findAll();

            return PaginationGiangVien.builder()
                    .count(_listGiangVien.size())
                    .data(_listGiangVien)
                    .build();
        }

        if (inputs.checkPaginationVariableNull()) {
            String _nameSearch = inputs.getHoVaTen() == null ? null : "%" + inputs.getHoVaTen() + "%";

            List<GiangVien> _listGiangVien = giangVienRepository.getGiangVienByNameAndKhoaVienId(inputs.getKhoaVienIds(), _nameSearch, inputs.getId());

            return PaginationGiangVien.builder()
                    .count(_listGiangVien.size())
                    .data(_listGiangVien)
                    .build();
        }

        Pageable _pageable = PageRequest.of(inputs.getPage(), inputs.getSize());

        String _nameSearch = inputs.getHoVaTen() == null ? null : "%" + inputs.getHoVaTen() + "%";

        Page<GiangVien> _pageGiangVien = giangVienRepository.getGiangVienByNameAndKhoaVienId(inputs.getKhoaVienIds(), _nameSearch, inputs.getId(), _pageable);

        return PaginationGiangVien.builder()
                .count(_pageGiangVien.getNumberOfElements())
                .data(_pageGiangVien.getContent())
                .build();


    }

    public GiangVien themGiangVien(ThemGiangVienInputs inputs) {
        boolean _isExistGiangVien = chuyenNganhRepository.existsById(inputs.getChuyenNganhID());

        if (!_isExistGiangVien) {
            throw new ChuyenNganhIsNotExistExcepton();
        }
        Optional<ChuyenNganh> _chuyenNganhOptional = chuyenNganhRepository.findById(inputs.getChuyenNganhID());
        try {
            ChuyenNganh _chuyenNganh = _chuyenNganhOptional.get();
            GiangVien _giangVien = GiangVien.builder()
                    .ten(inputs.getTen())
                    .hoTenDem(inputs.getHoTenDem())
                    .avatar(inputs.getAvatar())
                    .gioiTinh(inputs.isGioiTinh())
                    .soDienThoai(inputs.getSoDienThoai())
                    .email(inputs.getEmail())
                    .hocHam(inputs.getHocHam())
                    .chuyenNganh(_chuyenNganh)
                    .build();
            GiangVien _giangVienRes = giangVienRepository.saveAndFlush(_giangVien);
            return _giangVienRes;
        } catch (NoSuchElementException e) {
            throw new ChuyenNganhIsNotExistExcepton();
        }
    }

    public List<GiangVien> xoaGiangViens(Set<Long> ids) {
        List<GiangVien> _giangViens = giangVienRepository.findAllById(ids);
        if (_giangViens.isEmpty())
            throw new GiangVienIsNotExistException();
        List<Long> _ids = _giangViens.stream().map(i -> i.getId()).collect(Collectors.toList());
        giangVienRepository.xoaGiangViens(_ids);
        return _giangViens;
    }
}
