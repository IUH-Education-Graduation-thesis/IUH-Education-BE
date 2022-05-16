package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.*;
import com.hong_hoan.iuheducation.exception.*;
import com.hong_hoan.iuheducation.repository.*;
import com.hong_hoan.iuheducation.resolvers.input.lich_hoc.ThemLichHocInputs;
import com.hong_hoan.iuheducation.resolvers.response.lich_hoc.LichHocFormat;
import com.hong_hoan.iuheducation.util.HelperComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LichHocService {
    @Autowired
    private PhongHocRepository phongHocRepository;
    @Autowired
    private LichHocRepository lichHocRepository;
    @Autowired
    private HelperComponent helperComponent;
    @Autowired
    private GiangVienRepository giangVienRepository;
    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;
    @Autowired
    private NotificationRepository notificationRepository;

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
                if (_listLichHoc.get(j).getNgayHocTrongTuan() - 2 == i) {
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


    public List<LichHoc> xoaLichHocs(List<Long> ids) {
        List<LichHoc> _listLichHoc = lichHocRepository.findAllById(ids);

        if (_listLichHoc.size() <= 0) {
            throw new LichHocIsNotExistException();
        }

        List<Long> _ids = _listLichHoc.stream().map(i -> i.getId()).collect(Collectors.toList());

        lichHocRepository.xoaLichHocs(_ids);

        List<LopHocPhan> _listLopHocPhan = _listLichHoc.stream().map(i -> i.getLopHocPhan()).collect(Collectors.toList());


        for (LopHocPhan _lopHocPhan : _listLopHocPhan) {
            List<SinhVien> _listSinhVien = _lopHocPhan.getSinhVienLopHocPhans()
                    .stream().map(i -> i.getSinhVien()).collect(Collectors.toList());

            Notification _notification = Notification.builder()
                    .type(NotiType.LH)
                    .message("Lớp học phần '" + _lopHocPhan.getTenLopHocPhan() + "' vừa xóa 1 lịch học.")
                    .sinhViens(new HashSet<>(_listSinhVien))
                    .isRead(false)
                    .createDate(new Date())
                    .build();

            notificationRepository.saveAndFlush(_notification);
        }


        return _listLichHoc;
    }

    public LichHoc suaLichHoc(ThemLichHocInputs inputs, Long id) {
        Optional<LichHoc> _lichHocOption = lichHocRepository.findById(id);

        if (_lichHocOption.isEmpty()) {
            throw new LichHocIsNotExistException();
        }

        LichHoc _lichHoc = _lichHocOption.get();

        PhongHoc _phongHoc = null;

        try {
            _phongHoc = phongHocRepository.getById(inputs.getPhongHocId());
        } catch (Exception ex) {

        }

        GiangVien _giangVien = null;

        try {
            _giangVien = giangVienRepository.getById(inputs.getGiangVienId());
        } catch (Exception ex) {

        }

        Optional<LopHocPhan> _lopHocPhanOption = lopHocPhanRepository.findById(inputs.getLopHocPhanId());
        if (_lichHocOption.isEmpty()) {
            throw new LopHocPhanIsNotExist();
        }

        LopHocPhan _lopHocPhan = _lopHocPhanOption.get();

        Integer _nhomThucHanh = null;

        try {
            Integer __nhomThucHanh = inputs.getNhomThucHanh();
            Integer _soNhomThuHanh = _lopHocPhan.getSoNhomThucHanh();
            if (__nhomThucHanh > _soNhomThuHanh) {
                throw new GroupPraticeOver();
            }

            _nhomThucHanh = __nhomThucHanh;
        } catch (NullPointerException ex) {
            _nhomThucHanh = null;
        }

        if (inputs.getTietHocBatDau() > inputs.getTietHocKetThuc()) {
            throw new ValueOver();
        }

        Integer _soTinChiLyThuyet = _lopHocPhan.getHocPhan().getSoTinChiLyThuyet();
        Integer _soTinChiThucHanh = _lopHocPhan.getHocPhan().getSoTinChiThucHanh();

        Integer _lapLai = 0;

        if (inputs.getIsLichThi() || inputs.getIsHocBu()) {
            _lapLai = 1;
        } else {
            if (_nhomThucHanh == null) {
                _lapLai = (15 * _soTinChiLyThuyet) / (inputs.getTietHocKetThuc() - inputs.getTietHocBatDau());
            } else {
                _lapLai = (30 * _soTinChiThucHanh) / (inputs.getTietHocKetThuc() - inputs.getTietHocBatDau());
            }
        }

        _lichHoc.setGhiChu(inputs.getGhiChu());
        _lichHoc.setNgayHocTrongTuan(inputs.getNgayHocTrongTuan());
        _lichHoc.setNhomThucHanh(_nhomThucHanh);
        _lichHoc.setThoiGianBatDau(inputs.getThoiGianBatDau());
        _lichHoc.setLapLai(_lapLai);
        _lichHoc.setTietHocBatDau(inputs.getTietHocBatDau());
        _lichHoc.setTietHocKetThuc(inputs.getTietHocKetThuc());
        _lichHoc.setLopHocPhan(_lopHocPhan);
        _lichHoc.setPhongHoc(_phongHoc);
        _lichHoc.setGiangVien(_giangVien);
        _lichHoc.setLichThi(inputs.getIsLichThi());

        LichHoc _lichHocRes = lichHocRepository.saveAndFlush(_lichHoc);

        List<SinhVien> _listSinhVien = _lopHocPhan.getSinhVienLopHocPhans()
                .stream().map(i -> i.getSinhVien()).collect(Collectors.toList());

        Notification _notification = Notification.builder()
                .createDate(new Date())
                .isRead(false)
                .message("Lớp học phần '" + _lopHocPhan.getTenLopHocPhan() + "' đã sửa 1 lịch học.")
                .type(NotiType.LH)
                .sinhViens(new HashSet<>(_listSinhVien))
                .build();

        notificationRepository.saveAndFlush(_notification);

        return _lichHocRes;
    }

    public LichHoc themLichHoc(ThemLichHocInputs inputs) {


        PhongHoc _phongHoc = null;

        try {
            _phongHoc = phongHocRepository.getById(inputs.getPhongHocId());
        } catch (Exception ex) {

        }

        GiangVien _giangVien = null;

        try {
            _giangVien = giangVienRepository.getById(inputs.getGiangVienId());
        } catch (Exception ex) {

        }

        LopHocPhan _lopLopHocPhan = lopHocPhanRepository.getById(inputs.getLopHocPhanId());

        if (_lopLopHocPhan == null) {
            throw new LopHocPhanIsNotExist();
        }

        Integer _nhomThucHanh = null;

        try {
            Integer __nhomThucHanh = inputs.getNhomThucHanh();
            Integer _soNhomThuHanh = _lopLopHocPhan.getSoNhomThucHanh();
            if (__nhomThucHanh > _soNhomThuHanh) {
                throw new GroupPraticeOver();
            }

            _nhomThucHanh = __nhomThucHanh;
        } catch (NullPointerException ex) {
            _nhomThucHanh = null;
        }

        if (inputs.getTietHocBatDau() > inputs.getTietHocKetThuc()) {
            throw new ValueOver();
        }

        Integer _soTinChiLyThuyet = _lopLopHocPhan.getHocPhan().getSoTinChiLyThuyet();
        Integer _soTinChiThucHanh = _lopLopHocPhan.getHocPhan().getSoTinChiThucHanh();

        Integer _lapLai = 0;

        if (inputs.getIsLichThi() || inputs.getIsHocBu()) {
            _lapLai = 1;
        } else {
            if (_nhomThucHanh == null) {
                _lapLai = (15 * _soTinChiLyThuyet) / (inputs.getTietHocKetThuc() - inputs.getTietHocBatDau());
            } else {
                _lapLai = (30 * _soTinChiThucHanh) / (inputs.getTietHocKetThuc() - inputs.getTietHocBatDau());
            }
        }

        LichHoc _lichHoc = LichHoc.builder()
                .ghiChu(inputs.getGhiChu())
                .ngayHocTrongTuan(inputs.getNgayHocTrongTuan())
                .nhomThucHanh(_nhomThucHanh)
                .thoiGianBatDau(inputs.getThoiGianBatDau())
                .lapLai(_lapLai)
                .tietHocBatDau(inputs.getTietHocBatDau())
                .tietHocKetThuc(inputs.getTietHocKetThuc())
                .lopHocPhan(_lopLopHocPhan)
                .phongHoc(_phongHoc)
                .giangVien(_giangVien)
                .isLichThi(inputs.getIsLichThi())
                .build();

        LichHoc _lichHocRes = lichHocRepository.saveAndFlush(_lichHoc);

        List<SinhVien> _listSinhVien = _lopLopHocPhan.getSinhVienLopHocPhans().stream().map(i -> i.getSinhVien()).collect(Collectors.toList());

        Notification _noti = Notification.builder()
                .type(NotiType.LH)
                .createDate(new Date())
                .message("Lớp học phần '" + _lopLopHocPhan.getTenLopHocPhan() + "'đã thêm lịch học.")
                .isRead(false)
                .sinhViens(new HashSet<>(_listSinhVien))
                .build();

        notificationRepository.saveAndFlush(_noti);

        return _lichHocRes;
    }
}
