package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.KhoaVien;
import com.hong_hoan.iuheducation.exception.KhoaVienIsNotExistException;
import com.hong_hoan.iuheducation.repository.KhoaVienRepository;
import com.hong_hoan.iuheducation.resolvers.input.khoa_vien.ThemKhoaVienInputs;
import com.hong_hoan.iuheducation.resolvers.input.khoa_vien.ThemKhoaVienInputs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KhoaVienSevice {
    @Autowired
    private KhoaVienRepository khoaVienRepository;

    public KhoaVien themKhoaVien(ThemKhoaVienInputs inputs){
        KhoaVien _khoaVien =KhoaVien.builder()
                .ten(inputs.getTen())
                .link(inputs.getLink())
                .moTa(inputs.getMoTa())
                .build();
        KhoaVien _khoaVienResponse = khoaVienRepository.saveAndFlush(_khoaVien);
        return _khoaVienResponse;
    }
    public List<KhoaVien> xoaKhoaViens(Set<Long> ids){
        List<KhoaVien> _khoaViens = khoaVienRepository.findAllById(ids);
        if(_khoaViens.isEmpty()) throw new KhoaVienIsNotExistException();
        List<Long> _ids = _khoaViens.stream().map(i->i.getId()).collect(Collectors.toList());
        khoaVienRepository.deleteAllById(_ids);
        return _khoaViens;
    }
}
