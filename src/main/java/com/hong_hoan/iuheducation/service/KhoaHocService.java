package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.Khoa;
import com.hong_hoan.iuheducation.exception.KhoaHocIsNotExist;
import com.hong_hoan.iuheducation.repository.KhoaRepository;
import com.hong_hoan.iuheducation.resolvers.input.khoa_hoc.ThemKhoaHocInputs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class KhoaHocService {
    @Autowired
    private KhoaRepository khoaRepository;

    public List<Khoa> findAllKhoaHoc() {
        List<Khoa> _khoa = khoaRepository.findAll();

        return _khoa;
    }

    public Khoa findById(String id) throws NumberFormatException {
        long _id = Long.valueOf(id);

        try {
            Khoa _khoa = khoaRepository.findById(_id).get();

            return _khoa;
        } catch (NoSuchElementException ex) {
            throw new KhoaHocIsNotExist();
        }
    }

    public void deleteKhoaHoc(String id) throws NumberFormatException {
        long _idLong = Long.valueOf(id);
        boolean _isExistKhoaHoc = khoaRepository.existsById(_idLong);

        if(!_isExistKhoaHoc) {
            throw new KhoaHocIsNotExist();
        }

        khoaRepository.deleteById(_idLong);
    }

    public Khoa createKhoaHoc(ThemKhoaHocInputs inputs) {
        Khoa _khoaInputs = Khoa.builder()
                .khoa(inputs.getKhoa())
                .moTa(inputs.getMoTa())
                .build();

        Khoa _khoaRes = khoaRepository.saveAndFlush(_khoaInputs);

        return _khoaRes;
    }

}
