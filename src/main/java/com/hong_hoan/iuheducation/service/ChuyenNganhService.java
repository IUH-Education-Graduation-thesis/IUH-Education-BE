package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.ChuyenNganh;
import com.hong_hoan.iuheducation.entity.KhoaVien;
import com.hong_hoan.iuheducation.exception.ChuyenNganhIsNotExistExcepton;
import com.hong_hoan.iuheducation.exception.KhoaVienIsNotExistException;
import com.hong_hoan.iuheducation.repository.ChuyenNganhRepository;
import com.hong_hoan.iuheducation.repository.KhoaVienRepository;
import com.hong_hoan.iuheducation.resolvers.input.chuyen_nganh.ThemChuyenNganhInputs;
import com.hong_hoan.iuheducation.resolvers.response.khoa_vien.KhoaVienResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChuyenNganhService {
    @Autowired
    private ChuyenNganhRepository chuyenNganhRepository;
    @Autowired
    private KhoaVienRepository khoaVienRepository;

    public ChuyenNganh themChuyenNganh(@NotNull ThemChuyenNganhInputs inputs) {
        boolean _isExistKhoaVien = khoaVienRepository.existsById(inputs.getKhoaVienID());

        if(!_isExistKhoaVien){
            throw new KhoaVienIsNotExistException();
        }
        Optional<KhoaVien> _khoaVienOptional = khoaVienRepository.findById(inputs.getKhoaVienID());
        try{
            KhoaVien _khoaVien = _khoaVienOptional.get();
            ChuyenNganh _chuyenNganh = ChuyenNganh.builder()
                    .ten(inputs.getTen())
                    .moTa(inputs.getMoTa())
                    .khoaVien(_khoaVien)
                    .build();
            ChuyenNganh _chuyenNganhRes = chuyenNganhRepository.saveAndFlush(_chuyenNganh);
            return _chuyenNganhRes;
        }catch (NoSuchElementException e){
            throw new KhoaVienIsNotExistException();
        }
    }

    public List<ChuyenNganh> xoaChuyenNganhs(Set<Long> ids){
        List<ChuyenNganh> _chuyenNganhs = chuyenNganhRepository.findAllById(ids);
        if(_chuyenNganhs.isEmpty()) throw new ChuyenNganhIsNotExistExcepton();
        List<Long> _ids = _chuyenNganhs.stream().map(i->i.getId()).collect(Collectors.toList());
        chuyenNganhRepository.deleteAllById(_ids);
        return _chuyenNganhs;
    }
}
