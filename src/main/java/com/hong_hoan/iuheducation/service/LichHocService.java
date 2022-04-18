package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.Account;
import com.hong_hoan.iuheducation.entity.LichHoc;
import com.hong_hoan.iuheducation.entity.PhongHoc;
import com.hong_hoan.iuheducation.entity.SinhVien;
import com.hong_hoan.iuheducation.exception.DayNhaIsNotExistException;
import com.hong_hoan.iuheducation.exception.PhongHocIsNotExist;
import com.hong_hoan.iuheducation.repository.LichHocRepository;
import com.hong_hoan.iuheducation.repository.PhongHocRepository;
import com.hong_hoan.iuheducation.resolvers.input.lich_hoc.ThemLichHocInputs;
import com.hong_hoan.iuheducation.resolvers.response.lich_hoc.LichHocFormat;
import com.hong_hoan.iuheducation.util.HelperComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LichHocService {
    @Autowired
    private PhongHocRepository phongHocRepository;
    @Autowired
    private LichHocRepository lichHocRepository;
    @Autowired
    private HelperComponent helperComponent;

    public List<LichHocFormat> getLichHoc(Date dateInput, Account account) {
        SinhVien _sinhVien = account.getSinhVien();
        List<String> _listDayInWeekName = Arrays.asList("Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ Nhật");

        List<Date> _dateInWeek = helperComponent.getDatesInWeek(dateInput, 2);
        List<LichHoc> _listLichHoc = lichHocRepository.getListLichHocOfSinhVienWithWeek(_sinhVien.getMaSinhVien(), dateInput, _dateInWeek.get(0), _dateInWeek.get(_dateInWeek.size() - 1));
        List<LichHocFormat> _lichHocFormatList = new ArrayList<>();

        for (int i = 0; i < _dateInWeek.size(); i++) {
            String _thuString = _listDayInWeekName.get(i);
            Integer _thuNumber = i + 2;

            List<LichHoc> _listLichHocInDay = new ArrayList<>();

            for (int j = 0; j < _listLichHoc.size(); j++) {
                if(_listLichHoc.get(j).getNgayHocTrongTuan() - 2 == i) {
                    _listLichHocInDay.add(_listLichHoc.get(j));
                }
            }

            LichHocFormat _lichHocFormat = LichHocFormat.builder()
                    .thu(_thuString)
                    .thuNumber(_thuNumber)
                    .listLichHoc(_listLichHocInDay)
                    .build();

            _lichHocFormatList.add(_lichHocFormat);
        }

        return _lichHocFormatList;
    }

    public LichHoc themLichHoc(ThemLichHocInputs inputs) {
        boolean _isExistPhongHoc = phongHocRepository.existsById(inputs.getPhongHoc().getId());

        if(!_isExistPhongHoc) {
            throw new PhongHocIsNotExist();
        }
        Optional<PhongHoc> _phongHocOptional = phongHocRepository.findById(inputs.getPhongHoc().getId());
        try {
            PhongHoc _phongHoc = _phongHocOptional.get();
            LichHoc _lichHoc = LichHoc.builder()
                    .ngayHocTrongTuan(inputs.getNgayHocTrongTuan())
                    .nhomThucHanh(inputs.getNhomThucHanh())
                    .thoiGianBatDau(inputs.getThoiGianBatDau())
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
