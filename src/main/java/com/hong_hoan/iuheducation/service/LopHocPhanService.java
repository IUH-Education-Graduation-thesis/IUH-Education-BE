package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.*;
import com.hong_hoan.iuheducation.exception.*;
import com.hong_hoan.iuheducation.repository.*;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.DangKyHocPhanInputs;
import com.hong_hoan.iuheducation.resolvers.input.lop_hoc_phan.ThemLopHocPhanInputs;
import com.hong_hoan.iuheducation.resolvers.response.lop_hoc_phan.CheckLichHocRes;
import com.hong_hoan.iuheducation.resolvers.response.lop_hoc_phan.NgayTrongTuan;
import com.hong_hoan.iuheducation.util.HelperComponent;
import org.apache.commons.lang3.Range;
import org.apache.commons.math3.analysis.function.Sinh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LopHocPhanService {
    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;
    @Autowired
    private HocPhanRepository hocPhanRepository;
    @Autowired
    private HocKyNormalRepository hocKyNormalRepository;
    @Autowired
    private SinhVienRepository sinhVienRepository;
    @Autowired
    private SinhVienLopHocPhanRepository sinhVienLopHocPhanRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private HelperComponent helperComponent;
    @Autowired
    private LichHocRepository lichHocRepository;

    public CheckLichHocRes checkLichHoc(List<DangKyHocPhanInputs> listLopHocPhanPrepareDangKy, Long hocKyNormalId, Account account) {
        SinhVien _sinhVien = account.getSinhVien();

        List<LichHoc> _listLichHocPrepareDangKy = listLopHocPhanPrepareDangKy.stream()
                .map(i -> {
                    List<LichHoc> _listLichHoc = lichHocRepository.getListLichHocByLopHocPhanAndNhomThucHanhAndHocKy(hocKyNormalId, i.getNhomThucHanh(), i.getLopHocPhanId());
                    return _listLichHoc;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        List<LichHoc> _listLichHocSinhVienDaDangKy = lichHocRepository.getListLichHocCuaHocSinhDaDangKyTrongHocKy(_sinhVien.getId(), hocKyNormalId);

        List<LichHoc> _listLichHoc = Stream.concat(_listLichHocSinhVienDaDangKy.stream(), _listLichHocPrepareDangKy.stream())
                .collect(Collectors.toList());

        List<LichHoc> _listLichHocAfterFilter = _listLichHoc.stream()
                .filter(i -> {
                    Range<Integer> _range = Range.between(i.getTietHocBatDau(), i.getTietHocKetThuc());

                    Long _count = _listLichHoc.stream().filter(j -> {
                                Boolean _isTrungThu = i.getNgayHocTrongTuan() == j.getNgayHocTrongTuan();
                                Boolean _overLap = _range.isOverlappedBy(Range.between(j.getTietHocBatDau(), j.getTietHocKetThuc()));

                                if (!_isTrungThu || !_overLap) {
                                    return false;
                                }

                                return true;
                            }
                    ).count();

                    if (_count >= 2) {
                        return true;
                    }

                    return false;
                })
                .collect(Collectors.toList());

        List<NgayTrongTuan> _listNgayTrongTuan = Stream.of(
                NgayTrongTuan.builder()
                        .thu("Thứ 2")
                        .thuNumber(2)
                        .lichHocs(new ArrayList<>())
                        .build(),
                NgayTrongTuan.builder()
                        .thu("Thứ 3")
                        .thuNumber(3)
                        .lichHocs(new ArrayList<>())
                        .build(),
                NgayTrongTuan.builder()
                        .thu("Thứ 4")
                        .thuNumber(4)
                        .lichHocs(new ArrayList<>())
                        .build(),
                NgayTrongTuan.builder()
                        .thu("Thứ 5")
                        .thuNumber(5)
                        .lichHocs(new ArrayList<>())
                        .build(),
                NgayTrongTuan.builder()
                        .thu("Thứ 6")
                        .thuNumber(6)
                        .lichHocs(new ArrayList<>())
                        .build(),
                NgayTrongTuan.builder()
                        .thu("Thứ 7")
                        .thuNumber(7)
                        .lichHocs(new ArrayList<>())
                        .build(),
                NgayTrongTuan.builder()
                        .thu("Chủ nhật")
                        .lichHocs(new ArrayList<>())
                        .thuNumber(8)
                        .build()
        ).collect(Collectors.toList());

        for (LichHoc i : _listLichHocAfterFilter) {
            _listNgayTrongTuan.get(i.getNgayHocTrongTuan() - 2)
                    .getLichHocs().add(i);
        }

        CheckLichHocRes _checkLichHocRes = CheckLichHocRes.builder()
                .isTrung(_listLichHocAfterFilter.size() > 0)
                .listNgayTrongTuan(_listNgayTrongTuan)
                .build();

        return _checkLichHocRes;
    }

    public List<SinhVien> xoaSinhVienOfLopHocPhan(List<Long> sinhVienIds, Long lopHocPhanId) {
        Optional<LopHocPhan> _lopHocPhanOption = lopHocPhanRepository.findById(lopHocPhanId);

        if (_lopHocPhanOption.isEmpty()) {
            throw new LopHocPhanIsNotExist();
        }

        List<SinhVien> _listSinhVienFind = sinhVienRepository.findAllById(sinhVienIds);

        List<SinhVienLopHocPhan> _sinhVienLopHocPhan = _listSinhVienFind.stream().map(i -> {
            SinhVienLopHocPhanId _sinhVienLopHocPhanId = SinhVienLopHocPhanId.builder()
                    .lopHocPhanId(lopHocPhanId)
                    .sinhVienId(i.getId())
                    .build();

            Optional<SinhVienLopHocPhan> _sinhVienLopHocPhanOptional = sinhVienLopHocPhanRepository.findById(_sinhVienLopHocPhanId);

            if (_sinhVienLopHocPhanOptional.isEmpty()) {
                return null;

            }
            return _sinhVienLopHocPhanOptional.get();


        }).filter(i -> i != null).collect(Collectors.toList());

        LopHocPhan _lopHocPhan = _lopHocPhanOption.get();

        List<SinhVienLopHocPhanId> _listSinhVienLopHocPhanId = _sinhVienLopHocPhan.stream().map(i -> i.getSinhVienLopHocPhanId()).collect(Collectors.toList());

        List<SinhVienLopHocPhan> _listNewSinhVienLopHocPhan = _lopHocPhan.getSinhVienLopHocPhans().stream().filter(i -> {
            Integer _indexOf = _listSinhVienLopHocPhanId.indexOf(i.getSinhVienLopHocPhanId());

            if (_indexOf >= 0) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());

        _lopHocPhan.setSinhVienLopHocPhans(new HashSet<>(_listNewSinhVienLopHocPhan));

        List<SinhVien> _listSinhVien = _sinhVienLopHocPhan.stream().map(i -> i.getSinhVien()).collect(Collectors.toList());

        Notification _notification = Notification.builder()
                .sinhViens(new HashSet<>(_listSinhVien))
                .isRead(false)
                .createDate(new Date())
                .message("Bạn bị xóa khỏi lớp học phần '" + _lopHocPhan.getTenLopHocPhan() + "'")
                .type(NotiType.LHP)
                .build();

        lopHocPhanRepository.saveAndFlush(_lopHocPhan);
        notificationRepository.saveAndFlush(_notification);
        return _listSinhVien;
    }

    public List<SinhVienLopHocPhan> themSinhVienVaoLopHocPhan(Long lopHocPhanId, Set<Long> sinhVienIds, Integer nhomThucHanh) {
        LopHocPhan _lopHocPhan = lopHocPhanRepository.getById(lopHocPhanId);

        if (_lopHocPhan == null) {
            throw new LopHocPhanIsNotExist();
        }

        Integer _soNhomThucHanh = _lopHocPhan.getSoNhomThucHanh();

        if (_soNhomThucHanh > 0 && nhomThucHanh == null) {
            throw new EnterNhomThucHanh();
        }

        if (nhomThucHanh > _soNhomThucHanh) {
            throw new ValueOver();
        }

        List<SinhVien> _listSinhVien = sinhVienRepository.findAllById(sinhVienIds);

        List<SinhVien> _listSinhVienInLopHocPhan = sinhVienRepository.findSinhVienInLopHocPhan(lopHocPhanId);

        List<Long> _listSinhVienInLopHocPhanId = _listSinhVienInLopHocPhan.stream().map(i -> i.getId()).collect(Collectors.toList());

        List<SinhVien> _listSinhVienFilter = _listSinhVien.stream().filter(i -> {
            Integer _indexOf = _listSinhVienInLopHocPhanId.indexOf(i.getId());

            return _indexOf < 0;
        }).collect(Collectors.toList());

        List<SinhVienLopHocPhan> _listSinhVienLopHocPhan = _listSinhVienFilter.stream().map(i -> {
            SinhVienLopHocPhanId _sinhVienLopHocPhanId = SinhVienLopHocPhanId.builder()
                    .sinhVienId(i.getId())
                    .lopHocPhanId(_lopHocPhan.getId())
                    .build();

            return SinhVienLopHocPhan.builder()
                    .sinhVienLopHocPhanId(_sinhVienLopHocPhanId)
                    .sinhVien(i)
                    .lopHocPhan(_lopHocPhan)
                    .nhomThucHanh(nhomThucHanh)
                    .build();
        }).collect(Collectors.toList());

        List<SinhVienLopHocPhan> _listSinhVienLopHocPhanRes = sinhVienLopHocPhanRepository.saveAllAndFlush(_listSinhVienLopHocPhan);

        List<SinhVien> _listSinhVienRes = _listSinhVienLopHocPhanRes.stream().map(i -> i.getSinhVien()).collect(Collectors.toList());

        Notification _notification = Notification.builder()
                .createDate(new Date())
                .isRead(false)
                .message("Bạn được thêm vào lớp học phần '" + _lopHocPhan.getTenLopHocPhan() + "'")
                .type(NotiType.LHP)
                .sinhViens(new HashSet<>(_listSinhVienRes))
                .build();

        notificationRepository.saveAndFlush(_notification);

        return _listSinhVienLopHocPhanRes;
    }

    public LopHocPhan themLopHocPhan(ThemLopHocPhanInputs inputs) {
        HocPhan _hocPhan = hocPhanRepository.getById(inputs.getHocPhanId());

        if (_hocPhan == null) {
            throw new HocPhanIsNotExist();
        }

        HocKyNormal _hocKyNormal = hocKyNormalRepository.getById(inputs.getHocKyNormalId());

        if (_hocKyNormal == null) {
            throw new HocKyNormalIsNotExist();
        }

        String _maHocPhan = _hocPhan.getMaHocPhan();

        Integer _maxId = 0;
        Integer _maxIsRes = lopHocPhanRepository.getMaxId();

        if (_maxIsRes != null) {
            _maxId = _maxIsRes;
        }

        String _idLopHocPhanPadding = helperComponent.byPaddingZeros(_maxId, 5);
        String _maLopHocPhan = _maHocPhan + _idLopHocPhanPadding;

        LopHocPhan _lopHocPhan = LopHocPhan.builder()
                .hocKyNormal(_hocKyNormal)
                .hocPhan(_hocPhan)
                .maLopHocPhan(_maLopHocPhan)
                .lopDuKien(inputs.getLopDuKien())
                .moTa(inputs.getMoTa())
                .trangThaiLopHocPhan(TrangThaiLopHocPhan.DANG_LEN_KE_HOACH)
                .soLuongToiDa(inputs.getSoLuongToiDa())
                .soNhomThucHanh(inputs.getSoNhomThucHanh())
                .build();

        LopHocPhan _lopHocPhanRes = lopHocPhanRepository.saveAndFlush(_lopHocPhan);

        List<SinhVien> _listSinhVien = sinhVienRepository.getListSinhOfHocPhanButNotYetRegistry(_lopHocPhan.getHocPhan().getId());

        Notification _notification = Notification.builder()
                .createDate(new Date())
                .type(NotiType.LHP)
                .isRead(false)
                .sinhViens(new HashSet<>(_listSinhVien))
                .message("Học phần '" + _lopHocPhan.getHocPhan().getMonHoc().getTen() + "' Đã mở 1 lớp học phần.")
                .build();

        notificationRepository.saveAndFlush(_notification);

        return _lopHocPhanRes;
    }

    public LopHocPhan suaLopHocPhan(ThemLopHocPhanInputs inputs, Long id) {
        LopHocPhan _lopHocPhan = lopHocPhanRepository.getById(id);

        if (_lopHocPhan == null) {
            throw new LopHocPhanIsNotExist();
        }

        HocPhan _hocPhan = hocPhanRepository.getById(inputs.getHocPhanId());

        if (_hocPhan == null) {
            throw new HocPhanIsNotExist();
        }

        HocKyNormal _hocKyNormal = hocKyNormalRepository.getById(inputs.getHocKyNormalId());

        if (_hocKyNormal == null) {
            throw new HocKyNormalIsNotExist();
        }

        _lopHocPhan.setHocPhan(_hocPhan);
        _lopHocPhan.setHocKyNormal(_hocKyNormal);
        _lopHocPhan.setLopDuKien(inputs.getLopDuKien());
        _lopHocPhan.setMoTa(inputs.getMoTa());
        _lopHocPhan.setSoNhomThucHanh(inputs.getSoNhomThucHanh());
        _lopHocPhan.setSoLuongToiDa(inputs.getSoLuongToiDa());
        _lopHocPhan.setTrangThaiLopHocPhan(inputs.getTrangThaiLopHocPhan());

        LopHocPhan _lopHocPhanRes = lopHocPhanRepository.saveAndFlush(_lopHocPhan);

        if (!_lopHocPhan.getTrangThaiLopHocPhanEnum().equals(inputs.getTrangThaiLopHocPhan())) {
            List<SinhVien> _listSinhVien = _lopHocPhan.getSinhVienLopHocPhans().stream().map(i -> i.getSinhVien()).collect(Collectors.toList());

            Notification _notification = Notification.builder()
                    .createDate(new Date())
                    .isRead(false)
                    .message("Lớp học phần " + _lopHocPhan.getTenLopHocPhan() + " đã thay đổi trạng thái thành '" + _lopHocPhanRes.getTrangThaiLopHocPhan() + "'")
                    .type(NotiType.LHP)
                    .sinhViens(new HashSet<>(_listSinhVien))
                    .build();

            notificationRepository.saveAndFlush(_notification);
        }

        return _lopHocPhanRes;
    }

    public List<LopHocPhan> getLopHocPhanWithId(String id) {
        List<LopHocPhan> _listLopHocPhan = lopHocPhanRepository.getListLopHocPhanWithFilter(id);
        return _listLopHocPhan;
    }

    ;

    public List<LopHocPhan> getLopHocPhanDaDangKy(Long hocKyId, Account account) {
        SinhVien _sinhVien = account.getSinhVien();
        List<LopHocPhan> _listLopHocPhan = lopHocPhanRepository.getListLopHocPhanDangKyByHocKyAndSinhVien(_sinhVien.getId(), hocKyId);

        return _listLopHocPhan;
    }

}
