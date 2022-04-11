package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.Account;
import com.hong_hoan.iuheducation.entity.LopHocPhan;
import com.hong_hoan.iuheducation.entity.SinhVienLopHocPhan;
import com.hong_hoan.iuheducation.entity.SinhVienLopHocPhanId;
import com.hong_hoan.iuheducation.repository.LopHocPhanRepository;
import com.hong_hoan.iuheducation.repository.SinhVienLopHocPhanRepository;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.DangKyHocPhanInputs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SinhVienLopHocPhanService {
    @Autowired
    private SinhVienLopHocPhanRepository sinhVienLopHocPhanRepository;
    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;

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
