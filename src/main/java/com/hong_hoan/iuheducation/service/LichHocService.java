package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.LichHoc;
import com.hong_hoan.iuheducation.entity.PhongHoc;
import com.hong_hoan.iuheducation.exception.DayNhaIsNotExistException;
import com.hong_hoan.iuheducation.exception.PhongHocIsNotExist;
import com.hong_hoan.iuheducation.repository.LichHocRepository;
import com.hong_hoan.iuheducation.repository.PhongHocRepository;
import com.hong_hoan.iuheducation.resolvers.input.lich_hoc.ThemLichHocInputs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LichHocService {
    @Autowired
    private PhongHocRepository phongHocRepository;
    @Autowired
    private LichHocRepository lichHocRepository;

    public LichHoc themLichHoc(ThemLichHocInputs inputs) {
        boolean _isExistPhongHoc = phongHocRepository.existsById(inputs.getId());

        if(!_isExistPhongHoc) {
            throw new PhongHocIsNotExist();
        }
        Optional<PhongHoc> _phongHocOptional = phongHocRepository.findById(inputs.getId());
        try {
            PhongHoc _phongHoc = _phongHocOptional.get();
            LichHoc _lichHoc = LichHoc.builder()
                    .ngayHocTrongTuan(inputs.getNgayHocTrongTuan())
                    .nhomThucHanh(inputs.getNhomThucHanh())
                    .thoiGianBatDau(inputs.getThoiGianBatDau())
                    .thoiGianKetThuc(inputs.getThoiGianKetThuc())
                    .tietHocBatDau(inputs.getTietHocBatDau())
                    .tietHocKetThuc(inputs.getTietHocKetThuc())
                    .ghiChu(inputs.getGhiChu())
                    .build();
            LichHoc _lichHocRes = lichHocRepository.saveAndFlush(_lichHoc);
            return _lichHocRes;

        } catch (NoSuchElementException ex) {
            throw new DayNhaIsNotExistException();
        }
    }
}
