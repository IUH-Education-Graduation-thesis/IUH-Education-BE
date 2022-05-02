package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.NamHoc;
import com.hong_hoan.iuheducation.repository.NamHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NamHocService {
    @Autowired
    private NamHocRepository namHocRepository;

    public List<NamHoc> getAllNamHoc() {
        return namHocRepository.findAll();
    }
}
