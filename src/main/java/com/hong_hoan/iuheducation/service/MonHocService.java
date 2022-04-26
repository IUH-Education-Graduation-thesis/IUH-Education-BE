package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.ChuyenNganh;
import com.hong_hoan.iuheducation.entity.KhoaVien;
import com.hong_hoan.iuheducation.entity.MonHoc;
import com.hong_hoan.iuheducation.exception.ChuyenNganhIsNotExistExcepton;
import com.hong_hoan.iuheducation.exception.KhoaVienIsNotExistException;
import com.hong_hoan.iuheducation.repository.KhoaVienRepository;
import com.hong_hoan.iuheducation.repository.MonHocRepository;
import com.hong_hoan.iuheducation.resolvers.input.mon_hoc.ThemMonHocInputs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonHocService {
    @Autowired
    private MonHocRepository monHocRepository;
    @Autowired
    private KhoaVienRepository khoaVienRepository;

    public MonHoc themMonHoc(ThemMonHocInputs inputs){
        boolean _isExistKhoaVien = khoaVienRepository.existsById(inputs.getKhoaVienID());
        if(!_isExistKhoaVien) {throw new KhoaVienIsNotExistException();}

        Optional<KhoaVien> _khoaVienOptional = khoaVienRepository.findById(inputs.getKhoaVienID());
        try {
            KhoaVien _khoaVien = _khoaVienOptional.get();
            MonHoc _monHoc = MonHoc.builder()
                    .ten(inputs.getTen())
                    .moTa(inputs.getMoTa())
                    .khoaVien(_khoaVien)
                    .build();
            MonHoc _monHocRes = monHocRepository.saveAndFlush(_monHoc);
            return _monHocRes;
        }catch (NoSuchElementException e){
            throw new KhoaVienIsNotExistException();
        }
    }

    public List<MonHoc> xoaMonHocs(Set<Long> ids) {
        List<MonHoc> _monHocs = monHocRepository.findAllById(ids);
        if (_monHocs.isEmpty())
            throw new KhoaVienIsNotExistException();
        List<Long> _ids = _monHocs.stream().map(i -> i.getId()).collect(Collectors.toList());
        monHocRepository.deleteAllById(_ids);
        return _monHocs;
    }
}
