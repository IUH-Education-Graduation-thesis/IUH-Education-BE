package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.*;
import com.hong_hoan.iuheducation.repository.LopHocPhanRepository;
import com.hong_hoan.iuheducation.repository.SinhVienLopHocPhanRepository;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.DangKyHocPhanInputs;
import com.hong_hoan.iuheducation.resolvers.response.sinh_vien_lop_hoc_phan.HocKyItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SinhVienLopHocPhanService {
    @Autowired
    private SinhVienLopHocPhanRepository sinhVienLopHocPhanRepository;
    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;
    @Autowired
    private HocKyNormalService hocKyNormalService;

    public List<HocKyItem> getSinhVienLopHocPhanOfSinhVien(Account account) {
        List<HocKyNormal> _hocKyNormal = hocKyNormalService.getListHocKyNormalOfSinhVien(account);

        List<HocKyItem> _hocKyItem = _hocKyNormal.stream().map(i -> {
            List<SinhVienLopHocPhan> _listSinhVienLopHocPhan = sinhVienLopHocPhanRepository.getSinhVienLopHocPhansBySinhVienIdAndHocKyId(account.getSinhVien().getId(), i.getId());

            return HocKyItem.builder()
                    .thuTuHocKy(i.getThuTuHocKy())
                    .namBatDau(i.getNamHoc().getNamBatDau())
                    .namKetThuc(i.getNamHoc().getNamKetThuc())
                    .listSinhVienLopHocPhan(_listSinhVienLopHocPhan)
                    .build();
        }).collect(Collectors.toList());

        return _hocKyItem;
    }

    public List<SinhVienLopHocPhan> themSinhVienLopHocPhan(List<DangKyHocPhanInputs> inputs, Account account) {
        List<SinhVienLopHocPhan> _sinhVienLopHocPhans = new ArrayList<>();

        inputs.forEach(i -> {
            LopHocPhan _lopHocPhan = lopHocPhanRepository.findById(i.getLopHocPhanId()).get();

            SinhVienLopHocPhanId _sinhVienLopHocPhanId = SinhVienLopHocPhanId.builder()
                    .lopHocPhanId(_lopHocPhan.getId())
                    .sinhVienId(account.getSinhVien().getId())
                    .build();

            SinhVienLopHocPhan _sinhVienLopHocPhan = SinhVienLopHocPhan.builder()
                    .sinhVienLopHocPhanId(_sinhVienLopHocPhanId)
                    .sinhVien(account.getSinhVien())
                    .lopHocPhan(_lopHocPhan)
                    .nhomThucHanh(i.getNhomThucHanh())
                    .build();

            _sinhVienLopHocPhans.add(_sinhVienLopHocPhan);
        });

        List<SinhVienLopHocPhan> _sinhVienLopHocPhanRes = sinhVienLopHocPhanRepository.saveAllAndFlush(_sinhVienLopHocPhans);

        return _sinhVienLopHocPhanRes;
    }

}
