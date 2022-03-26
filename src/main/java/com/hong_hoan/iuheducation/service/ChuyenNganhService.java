package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.ChuyenNganh;
import com.hong_hoan.iuheducation.repository.ChuyenNganhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ChuyenNganhService {
    @Autowired
    private ChuyenNganhRepository chuyenNganhRepository;

    public List<ChuyenNganh> findListChuyeNganh(String id) {
        try {
            boolean _idIsEmpty = id.isEmpty();
            long _idLong = Long.valueOf(id);

            ChuyenNganh _chuyenNganh = chuyenNganhRepository.getById(_idLong);

            return Arrays.asList(_chuyenNganh);

        } catch (NullPointerException ex) {
            List<ChuyenNganh> _listChuyenNganh = chuyenNganhRepository.findAll();

            return _listChuyenNganh;
        } catch (NumberFormatException ex) {
            return Arrays.asList();
        }
    }

}
