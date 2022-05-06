package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.*;
import com.hong_hoan.iuheducation.exception.HocKyIsNotExist;
import com.hong_hoan.iuheducation.exception.HocPhanIsNotExist;
import com.hong_hoan.iuheducation.exception.MonHocIsExistException;
import com.hong_hoan.iuheducation.repository.*;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.FindHocPhanInputs;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.GetHocPhanDKHP;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.KieuDangKy;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.ThemHocPhanInputs;
import com.hong_hoan.iuheducation.resolvers.response.hoc_phan.PaginationHocPhan;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HocPhanService {
    @Autowired
    private HocPhanRepository hocPhanRepository;
    @Autowired
    private HocKyNormalRepository hocKyNormalRepository;
    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;
    @Autowired
    private MonHocRepository monHocRepository;
    @Autowired
    private HocKyRepository hocKyRepository;

    @Transactional
    public void removeGroup(Long id) {
        HocPhan _hocPhan = hocPhanRepository.getById(id);

        if (_hocPhan == null) {
            throw new HocPhanIsNotExist();
        }

        _hocPhan.getHocKies().forEach(i -> i.getHocPhans().remove(_hocPhan));

        hocKyRepository.saveAll(_hocPhan.getHocKies());
        hocPhanRepository.delete(_hocPhan);
    }

    public List<HocPhan> xoaHocPhans(List<Long> ids) {
        List<HocPhan> _hocPhans = hocPhanRepository.findAllById(ids);

        if (_hocPhans.size() <= 0) {
            throw new HocPhanIsNotExist();
        }

        _hocPhans.forEach(i -> removeGroup(i.getId()));

//        List<Long> _ids = _hocPhans.stream().map(i -> i.getId()).collect(Collectors.toList());

//        hocPhanRepository.xoaHocPhans(_ids);

        return _hocPhans;
    }

    public HocPhan suaHocPhan(ThemHocPhanInputs inputs, Long id) {
        HocPhan _hocPhan = hocPhanRepository.getById(id);

        if (_hocPhan == null) {
            throw new HocPhanIsNotExist();
        }

        MonHoc _monHoc = monHocRepository.getById(inputs.getMonHocId());

        if (_monHoc == null) {
            throw new MonHocIsExistException();
        }

        HocKy _hocKy = hocKyRepository.getById(inputs.getHocKyId());

        if (_hocKy == null) {
            throw new HocKyIsNotExist();
        }


        Set<HocKy> _hocKies = _hocPhan.getHocKies();

        _hocKies.add(_hocKy);

        _hocPhan.setMaHocPhan(inputs.getMaHocPhan());
        _hocPhan.setMonHoc(_monHoc);
        _hocPhan.setHocKies(_hocKies);
        _hocPhan.setBatBuoc(inputs.isBatBuoc());
        _hocPhan.setMoTa(inputs.getMoTa());
        _hocPhan.setSoTinChiLyThuyet(inputs.getSoTinChiLyThuyet());
        _hocPhan.setSoTinChiThucHanh(inputs.getSoTinChiThucHanh());

        HocPhan _hocPhanRes = hocPhanRepository.saveAndFlush(_hocPhan);

        return _hocPhanRes;
    }

    public HocPhan themHocPhan(ThemHocPhanInputs inputs) {
        MonHoc _monHoc = monHocRepository.getById(inputs.getMonHocId());

        if (_monHoc == null) {
            throw new MonHocIsExistException();
        }

        HocKy _hocKy = hocKyRepository.getById(inputs.getHocKyId());

        if (_hocKy == null) {
            throw new HocKyIsNotExist();
        }

        HocPhan _hocPhan = HocPhan.builder().maHocPhan(inputs.getMaHocPhan()).moTa(inputs.getMoTa()).batBuoc(inputs.isBatBuoc()).monHoc(_monHoc).hocKies(new HashSet<>(Arrays.asList(_hocKy))).soTinChiLyThuyet(inputs.getSoTinChiLyThuyet()).soTinChiThucHanh(inputs.getSoTinChiThucHanh()).build();

        HocPhan _hocPhanRes = hocPhanRepository.saveAndFlush(_hocPhan);

        return _hocPhanRes;
    }

    public List<HocPhan> getListHocPhanForDKHP(Long hocKyNormalId, KieuDangKy kieuDangKy, Account account) {
        if (kieuDangKy == KieuDangKy.HOC_MOI) {
            List<HocPhan> _listHocPhan = hocPhanRepository.getListHocPhanForDangKyHocMoi(hocKyNormalId, account.getSinhVien().getId());
            for (int i = 0; i < _listHocPhan.size(); i++) {
                List<LopHocPhan> _listLopHocPhan = lopHocPhanRepository.getLopHocPhanByLopHocPhanDangKy(_listHocPhan.get(i).getId(), hocKyNormalId);
                _listHocPhan.get(i).setLopHocPhans(_listLopHocPhan);
            }

            return _listHocPhan;
        }


        return Arrays.asList();
    }

    public PaginationHocPhan findHocPhanWithPagination(FindHocPhanInputs inputs) {
        boolean _isInputsEmpty = ObjectUtils.isEmpty(inputs);

        if (_isInputsEmpty) {
            List<HocPhan> _listHocPhan = hocPhanRepository.findAll();

            return PaginationHocPhan.builder().count(_listHocPhan.size()).data(_listHocPhan).build();
        }

        if (inputs.checkAllDataIsNull()) {
            List<HocPhan> _listHocPhan = hocPhanRepository.findAll();

            return PaginationHocPhan.builder().count(_listHocPhan.size()).data(_listHocPhan).build();
        }

        if (inputs.checkPaginationIsNull()) {
            List<HocPhan> _listHocPhan = hocPhanRepository.filterHocPhan(inputs.getId(), inputs.getMaHocPhan(), inputs.getHocKyIds(), inputs.getKhoaIds(), inputs.getChuyenNganhIds(), inputs.getKhoaVienIds(), inputs.getMonHocIds());
            return PaginationHocPhan.builder().count(_listHocPhan.size()).data(_listHocPhan).build();
        }

        Pageable _pageable = PageRequest.of(inputs.getPage(), inputs.getSize());

        Page<HocPhan> _hocPhanPage = hocPhanRepository.filterHocPhan(inputs.getId(), inputs.getMaHocPhan(), inputs.getHocKyIds(), inputs.getKhoaIds(), inputs.getChuyenNganhIds(), inputs.getKhoaVienIds(), inputs.getMonHocIds(), _pageable);

        return PaginationHocPhan.builder().count(_hocPhanPage.getNumberOfElements()).data(_hocPhanPage.getContent()).build();

    }

}
