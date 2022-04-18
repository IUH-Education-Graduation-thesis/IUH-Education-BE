package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.Account;
import com.hong_hoan.iuheducation.entity.HocKyNormal;
import com.hong_hoan.iuheducation.repository.HocKyNormalRepository;
import com.hong_hoan.iuheducation.resolvers.response.hoc_ky_normal.HocKyNormalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HocKyNormalService {
    @Autowired
    private HocKyNormalRepository hocKyNormalRepository;

    public List<HocKyNormal> getListHocKyNormalOfSinhVien(Account account) {
        Date _ngayVaoTruong = account.getSinhVien().getNgayVaoTruong();

        List<HocKyNormal> _listHocKyNormal = hocKyNormalRepository.getListHocKyOfSinhVien(_ngayVaoTruong);

        return _listHocKyNormal;
    }
}
