package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.LopHocPhan;
import com.hong_hoan.iuheducation.repository.LopHocPhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LopHocPhanService {
    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;

    public List<LopHocPhan> getLopHocPhanWithId(String id) {
            List<LopHocPhan> _listLopHocPhan = lopHocPhanRepository.getListLopHocPhanWithFilter(id);
            return _listLopHocPhan;
    };

}