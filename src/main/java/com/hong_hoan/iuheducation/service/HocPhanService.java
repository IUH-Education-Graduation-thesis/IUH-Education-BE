package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.Account;
import com.hong_hoan.iuheducation.entity.HocKyNormal;
import com.hong_hoan.iuheducation.entity.HocPhan;
import com.hong_hoan.iuheducation.entity.LopHocPhan;
import com.hong_hoan.iuheducation.repository.HocKyNormalRepository;
import com.hong_hoan.iuheducation.repository.HocPhanRepository;
import com.hong_hoan.iuheducation.repository.LopHocPhanRepository;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.FindHocPhanInputs;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.GetHocPhanDKHP;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.KieuDangKy;
import com.hong_hoan.iuheducation.resolvers.response.hoc_phan.PaginationHocPhan;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class HocPhanService {
    @Autowired
    private HocPhanRepository hocPhanRepository;
    @Autowired
    private HocKyNormalRepository hocKyNormalRepository;
    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;

    public List<HocPhan> getListHocPhanForDKHP(Long hocKyNormalId, KieuDangKy kieuDangKy, Account account) {
        HocKyNormal _hocKyNormal = hocKyNormalRepository.getById(hocKyNormalId);

        Integer _namNhapHoc = account.getSinhVien().getNgayVaoTruong().getYear() + 1900;
        Integer _namDangHoc = _hocKyNormal.getNamHoc().getNamKetThuc() - _namNhapHoc;
        Integer _thuTuHocKyMaper = _namDangHoc + _hocKyNormal.getThuTuHocKy();

        System.out.println("_namNhapHoc = " + _namNhapHoc + ", _namDangHoc = " + _namDangHoc + ", _thuTuHocKyMaper = " + _thuTuHocKyMaper);

        if (kieuDangKy == KieuDangKy.HOC_MOI) {
//            List<HocPhan> _hocPhahanRepository.getListHocPhanHocMoi(account.getSinhVien().getLop().getKhoa().getId(), hocKyDangKy, account.getSinhVien().getId());
//            return _hocPhans;

            List<HocPhan> _listHocPhan = hocPhanRepository.getListHocPhanForDangKy(hocKyNormalId, _thuTuHocKyMaper);
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

            return PaginationHocPhan.builder()
                    .count(_listHocPhan.size())
                    .data(_listHocPhan)
                    .build();
        }

        if(inputs.checkAllDataIsNull()) {
            List<HocPhan> _listHocPhan = hocPhanRepository.findAll();

            return PaginationHocPhan.builder()
                    .count(_listHocPhan.size())
                    .data(_listHocPhan)
                    .build();
        }

        if (inputs.checkPaginationIsNull()) {
            List<HocPhan> _listHocPhan = hocPhanRepository.filterHocPhan(inputs.getId(), inputs.getMaHocPhan(), inputs.getNamHocIds(), inputs.getHocKyIds(), inputs.getMonHocIds(), inputs.getKhoaVienIds());
            return PaginationHocPhan.builder()
                    .count(_listHocPhan.size())
                    .data(_listHocPhan)
                    .build();
        }

        Pageable _pageable = PageRequest.of(inputs.getPage(), inputs.getSize());

        Page<HocPhan> _hocPhanPage = hocPhanRepository.filterHocPhan(inputs.getId(), inputs.getMaHocPhan(), inputs.getNamHocIds(), inputs.getHocKyIds(), inputs.getMonHocIds(), inputs.getKhoaVienIds(), _pageable);

        return PaginationHocPhan.builder()
                .count(_hocPhanPage.getNumberOfElements())
                .data(_hocPhanPage.getContent())
                .build();

    }

}
