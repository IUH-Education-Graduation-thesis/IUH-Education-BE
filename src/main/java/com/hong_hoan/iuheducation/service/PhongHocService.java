package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.PhongHoc;
import com.hong_hoan.iuheducation.repository.PhongHocRepository;
import lombok.RequiredArgsConstructor;
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
