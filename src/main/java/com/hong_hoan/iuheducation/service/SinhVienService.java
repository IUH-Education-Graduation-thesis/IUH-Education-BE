package com.hong_hoan.iuheducation.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import com.hong_hoan.iuheducation.entity.Account;
import com.hong_hoan.iuheducation.exception.SinhVienIsNotExist;
import com.hong_hoan.iuheducation.resolvers.response.sinh_vien.SuccessAndFailSinhVien;
import org.apache.commons.math3.analysis.function.Sinh;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SinhVienService {
    @Autowired
    private SinhVienRepository sinhVienRepository;
    @Autowired
    private LopRepository lopRepository;
    @Autowired
    private HelperComponent helperComponent;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public SinhVien suaSinhVien(SinhVienInputs inputs, Long sinhVienId) throws LopIsNotExist {
        SinhVien _sinhVien = sinhVienRepository.getById(sinhVienId);


        if (_sinhVien == null) {
            throw new SinhVienIsNotExist();
        }

        Lop _lop = lopRepository.getById(inputs.getLopId());

        if (_lop == null) {
            throw new LopIsNotExist();
        }

        _sinhVien.setBacDaoTao(inputs.getBacDaoTao());
        _sinhVien.setNgaySinh(inputs.getNgaySinh());
        _sinhVien.setDanToc(inputs.getDanToc());
        _sinhVien.setDiaChi(inputs.getDiaChi());
        _sinhVien.setEmail(inputs.getEmail());
        _sinhVien.setGioiTinh(inputs.isGioiTinh());
        _sinhVien.setHoTenDem(inputs.getHoTenDem());
        _sinhVien.setTen(inputs.getTen());
        _sinhVien.setLoaiHinhDaoTao(inputs.getLoaiHinhDaoTao());
        _sinhVien.setTrangThai(inputs.getTrangThai());
        _sinhVien.setTonGiao(inputs.getTonGiao());
        _sinhVien.setSoDienThoai(inputs.getSoDienThoai());
        _sinhVien.setSoCMND(inputs.getSoCMND());
        _sinhVien.setNoiSinh(inputs.getNoiSinh());
        _sinhVien.setNgayVaoTruong(inputs.getNgayVaoTruong());
        _sinhVien.setNgayVaoDoan(inputs.getNgayVaoDoan());
        _sinhVien.setNgayVaoDang(inputs.getNgayVaoDang());
        _sinhVien.setLop(_lop);
        _sinhVien.setAvatar(inputs.getAvatar());

        SinhVien _sinhVienSave = sinhVienRepository.saveAndFlush(_sinhVien);

        return _sinhVienSave;
    }

    public List<SinhVien> xoaSinhViens(List<Long> ids) {
        List<SinhVien> _listSinhVien = sinhVienRepository.findAllById(ids);
        if (_listSinhVien.size() <= 0) {
            throw new SinhVienIsNotExist();
        }

        List<Long> _listId = _listSinhVien.stream().map(i -> i.getId()).collect(Collectors.toList());

        sinhVienRepository.xoaSinhViens(_listId);

        return _listSinhVien;
    }

    public SuccessAndFailSinhVien addSinhVienWithFile(Part path) throws Throwable {
        InputStream _inputStream = path.getInputStream();
        Gson _gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

        Workbook _workbook = new XSSFWorkbook(_inputStream);

        XSSFSheet _sheet = (XSSFSheet) _workbook.getSheetAt(0);

        List<JsonObject> _listDataJson = new ArrayList<>();

        Row _header = _sheet.getRow(0);

        for (int i = 1; i < _sheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow _row = _sheet.getRow(i);
            JsonObject _rowJsonObject = new JsonObject();
            for (int j = 0; j < _header.getPhysicalNumberOfCells(); j++) {
                String _columnName = _header.getCell(j).toString();
                Cell _cell = _row.getCell(j);
                try {
                    switch (_cell.getCellType()) {
                        case BOOLEAN:
                            boolean _columnValue = _cell.getBooleanCellValue();
                            _rowJsonObject.addProperty(_columnName, _columnValue);
                            break;
                        case NUMERIC:
                            Double _number = _cell.getNumericCellValue();
                            _rowJsonObject.addProperty(_columnName, _number);
                            break;
                        default:
                            String _value = _cell.toString();
                            _rowJsonObject.addProperty(_columnName, _value);
                            break;
                    }
                } catch (NullPointerException ex) {
                    try {
                        String _columnValue = _cell.toString();
                        _rowJsonObject.addProperty(_columnName, _columnValue);

                    } catch (NullPointerException _ex) {
                        _rowJsonObject.addProperty(_columnName, (String) null);
                    }
                }
            }
            _listDataJson.add(_rowJsonObject);
        }

        List<SinhVien> _listSinhVien = new ArrayList<>();
        List<SinhVien> _listSinhVienFail = new ArrayList<>();


        _listDataJson.forEach(i -> {
            SinhVienInputs _sinhVienInputs = _gson.fromJson(i, SinhVienInputs.class);
            try {
                SinhVien _sinhVien = addSinhVien(_sinhVienInputs);
                _listSinhVien.add(_sinhVien);
            } catch (LopIsNotExist e) {
                e.printStackTrace();
                _listSinhVienFail.add(e.get_sinhVien());
            }

        });

        return SuccessAndFailSinhVien.builder()
                .sinhVienSuccess(_listSinhVien)
                .sinhVienFailure(_listSinhVienFail)
                .build();
    }

    public SinhVien addSinhVien(SinhVienInputs inputs) throws NumberFormatException, LopIsNotExist {

        long _lopId = Long.valueOf(inputs.getLopId());
        Lop _lop = lopRepository.getById(_lopId);

        if (_lop == null) {

            SinhVien _sinhVien = SinhVien.builder()
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

            throw new LopIsNotExist(_sinhVien);
        }

        Integer _maxIdExist = 0;
        String _maKhoa = helperComponent.byPaddingZeros(_lop.getKhoa().getChuyenNganh().getKhoaVien().getId().intValue(), 3);

        Integer _maxSinhVienIdRes = sinhVienRepository.getMaxId();

        if (_maxSinhVienIdRes != null) {
            _maxIdExist = _maxSinhVienIdRes + 1;
        }

        String _maSinhVien = Integer.toString(_lop.getKhoa().getKhoa()) + _maKhoa + helperComponent.byPaddingZeros(_maxIdExist, 3);

        Account _account = Account.builder()
                .userName(_maSinhVien)
                .password(passwordEncoder.encode("123456"))
                .roles(Set.of("STUDENT"))
                .build();

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
                .account(_account)
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
