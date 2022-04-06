package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.Lop;
import com.hong_hoan.iuheducation.entity.SinhVien;
import com.hong_hoan.iuheducation.exception.LopIsNotExist;
import com.hong_hoan.iuheducation.repository.LopRepository;
import com.hong_hoan.iuheducation.repository.SinhVienRepository;
import com.hong_hoan.iuheducation.resolvers.input.sinh_vien.FindSinhVienInputs;
import com.hong_hoan.iuheducation.resolvers.input.sinh_vien.SinhVienInputs;
import com.hong_hoan.iuheducation.resolvers.response.sinh_vien.PaginationSinhVien;
import com.hong_hoan.iuheducation.util.HelperComponent;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SinhVienService {
    @Autowired
    private SinhVienRepository sinhVienRepository;
    @Autowired
    private LopRepository lopRepository;
    @Autowired
    private HelperComponent helperComponent;

    public SinhVien addSinhVien(SinhVienInputs inputs) throws NumberFormatException, LopIsNotExist {

        long _lopId = Long.valueOf(inputs.getLopId());
        Lop _lop = lopRepository.getById(_lopId);

        if (_lop == null) {
            throw new LopIsNotExist();
        }

        Integer _maxIdExist = 0;
        String _maKhoa = helperComponent.byPaddingZeros(_lop.getKhoa().getChuyenNganh().getKhoaVien().getId().intValue(), 3);

        Integer _maxSinhVienIdRes = sinhVienRepository.getMaxId();

        if (_maxSinhVienIdRes != null) {
            _maxIdExist = _maxSinhVienIdRes + 1;
        }

        String _maSinhVien = Integer.toString(_lop.getKhoa().getKhoa()) + _maKhoa + helperComponent.byPaddingZeros(_maxIdExist, 3);

        SinhVien _sinhVien = SinhVien.builder()
                .maSinhVien(_maSinhVien)
                .maHoSo(_maSinhVien)
                .hoTenDem(inputs.getHoTenDem())
                .ten(inputs.getTen())
                .avatar(inputs.getAvatar())
                .gioiTinh(inputs.isGioiTinh())
                .ngayVaoDang(inputs.getNgayVaoDang())
                .ngayVaoTruong(inputs.getNgayVaoTruong())
                .ngayVaoDoan(inputs.getNgayVaoDoan())
                .ngaySinh(inputs.getNgaySinh())
                .soDienThoai(inputs.getSoDienThoai())
                .diaChi(inputs.getDiaChi())
                .noiSinh(inputs.getNoiSinh())
                .email(inputs.getEmail())
                .soCMND(inputs.getSoCMND())
                .bacDaoTao(inputs.getBacDaoTao())
                .trangThai(inputs.getTrangThai())
                .danToc(inputs.getDanToc())
                .tonGiao(inputs.getTonGiao())
                .loaiHinhDaoTao(inputs.getLoaiHinhDaoTao())
                .lop(_lop)
                .build();

        SinhVien _sinhVienRes = sinhVienRepository.saveAndFlush(_sinhVien);

        return _sinhVienRes;
    }

    public PaginationSinhVien findSinhVienWithFilter(FindSinhVienInputs inputs) {
        boolean _isInputsEmpty = ObjectUtils.isEmpty(inputs);

        if (_isInputsEmpty) {
            List<SinhVien> _listSinhVien = sinhVienRepository.findAll();

            return PaginationSinhVien.builder()
                    .count(_listSinhVien.size())
                    .data(_listSinhVien)
                    .build();
        }

        if (inputs.allDataIsEmpty()) {
            List<SinhVien> _listSinhVien = sinhVienRepository.findAll();

            return PaginationSinhVien.builder()
                    .count(_listSinhVien.size())
                    .data(_listSinhVien)
                    .build();
        }

        String _maSinhVienInputs = inputs.getMaSinhVien() == null ? null : "%" + inputs.getMaSinhVien() + "%";
        String _tenSinhVienInputs = inputs.getTenSinhVien() == null ? null : "%" + inputs.getTenSinhVien() + "%";

        if (inputs.checkPaginationEmpty()) {
            List<SinhVien> _listSinhVien = sinhVienRepository.findListSinhVienFilter(inputs.getId(), _maSinhVienInputs, _tenSinhVienInputs, inputs.getLopIds(), inputs.getChuyenNganhIds(), inputs.getKhoaVienIds(), inputs.getKhoaHocIds());

            return PaginationSinhVien.builder()
                    .count(_listSinhVien.size())
                    .data(_listSinhVien)
                    .build();
        }

        Pageable _pageable = PageRequest.of(inputs.getPage(), inputs.getSize());

        Page<SinhVien> _sinhVienPage = sinhVienRepository.findListSinhVienFilter(inputs.getId(), _maSinhVienInputs, _tenSinhVienInputs, inputs.getLopIds(), inputs.getChuyenNganhIds(), inputs.getKhoaVienIds(), inputs.getKhoaHocIds(), _pageable);

        return PaginationSinhVien.builder()
                .count(_sinhVienPage.getNumberOfElements())
                .data(_sinhVienPage.getContent())
                .build();
    }
}
