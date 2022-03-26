package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.DayNha;
import com.hong_hoan.iuheducation.entity.PhongHoc;
import com.hong_hoan.iuheducation.exception.DayNhaIsNotExistException;
import com.hong_hoan.iuheducation.exception.PhongHocIsNotExist;
import com.hong_hoan.iuheducation.repository.DayNhaRepository;
import com.hong_hoan.iuheducation.repository.PhongHocRepository;
import com.hong_hoan.iuheducation.resolvers.input.phong_hoc.ThemPhongHocInputs;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhongHocService {
    @Autowired
    private PhongHocRepository phongHocRepository;
    @Autowired
    private DayNhaRepository dayNhaRepository;

    public void xoaPhongHoc(long id) {
        boolean _isExistPhongHoc = phongHocRepository.existsById(id);
        if(!_isExistPhongHoc) throw new PhongHocIsNotExist();
        phongHocRepository.deleteById(id);
    }

    public PhongHoc themPhongHoc(@NotNull ThemPhongHocInputs inputs) {
        boolean _isExistDayNha = dayNhaRepository.existsById(inputs.getDayNhaId());

        if(!_isExistDayNha) {
            throw new DayNhaIsNotExistException();
        }

        Optional<DayNha> _dayNhaOptional = dayNhaRepository.findById(inputs.getDayNhaId());
        try {
            DayNha _dayNha = _dayNhaOptional.get();

            PhongHoc _phongHoc = PhongHoc.builder()
                    .dayNha(_dayNha)
                    .tenPhongHoc(inputs.getTenPhongHoc())
                    .moTa(inputs.getMoTa())
                    .sucChua(inputs.getSucChua())
                    .build();

            PhongHoc _phongHocRes = phongHocRepository.saveAndFlush(_phongHoc);

            return _phongHocRes;
        } catch (NoSuchElementException ex) {
            throw new DayNhaIsNotExistException();
        }
    }

    public List<PhongHoc> findAllPhongHoc(){
        List<PhongHoc> _listPhongHoc = phongHocRepository.findAll();

        return _listPhongHoc;
    }

    public List<PhongHoc> findPhongHocByDayNha(long dayNhaId) {
        List<PhongHoc> _listPhongHoc = phongHocRepository.findByDayNhaId(dayNhaId);

        return _listPhongHoc;
    }

    public PhongHoc findPhongHocById(long id) {
        Optional<PhongHoc> _phongHoc = phongHocRepository.findById(id);

        try {
            PhongHoc _phongHocReal = _phongHoc.get();
            return  _phongHocReal;
        } catch (NoSuchElementException ex) {
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
