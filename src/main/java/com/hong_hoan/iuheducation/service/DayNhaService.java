package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.DayNha;
import com.hong_hoan.iuheducation.entity.NamHoc;
import com.hong_hoan.iuheducation.exception.DayNhaIsNotExistException;
import com.hong_hoan.iuheducation.repository.DayNhaRepository;
import com.hong_hoan.iuheducation.resolvers.input.day_nha.FindDayNhaInputs;
import com.hong_hoan.iuheducation.util.Helper;
import com.hong_hoan.iuheducation.util.Merge;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DayNhaService {
    private Helper helper = Helper.getInstance();

    @Autowired
    private DayNhaRepository dayNhaRepository;


    public DayNha findDayNhaById(String id) {
        try {
            long _id = Long.valueOf(id);
            DayNha _dayNha = dayNhaRepository.findById(_id).get();
            return _dayNha;
        } catch (NumberFormatException ex) {
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<DayNha> getListDayNha(FindDayNhaInputs inputs) {
        if(ObjectUtils.isEmpty(inputs)) {
            return dayNhaRepository.findAll();
        }

        try {
            boolean _isEmptyId = inputs.getId().isEmpty();
            DayNha _dayNha = findDayNhaById(inputs.getId());
            if(_dayNha == null) {
                return Arrays.asList();
            }

            return Arrays.asList(_dayNha);

        }catch (NullPointerException ex) {
            List<DayNha> _listDayNha = dayNhaRepository.findByTenDayNhaContaining(inputs.getTenDayNha());
            return _listDayNha;
        }
    }

    public DayNha themDayNha(DayNha dayNha) {
        DayNha _dayNha = dayNhaRepository.saveAndFlush(dayNha);
        return _dayNha;
    }

    public DayNha updateDayNha(DayNha dayNha) throws IllegalAccessException, InstantiationException {
        boolean _isExist = dayNhaRepository.existsById(dayNha.getId());

        if (!_isExist) {
            throw new DayNhaIsNotExistException();
        }

        DayNha _dayNhaResult = dayNhaRepository.save(dayNha);

        return _dayNhaResult;
    }

    public List<DayNha> deleteDayNhas(Set<Long> ids) {
        List<DayNha> _dayNhas = dayNhaRepository.findAllById(ids);

        if(_dayNhas.isEmpty()) throw new DayNhaIsNotExistException();

        List<Long> _ids = _dayNhas.stream().map(i -> i.getId()).collect(Collectors.toList());

        dayNhaRepository.deleteAllById(_ids);

        return _dayNhas;
    }

    public void deleteDayNha(long dayNhaId) {
        boolean _isExist = dayNhaRepository.existsById(dayNhaId);

        if(!_isExist) {
            throw new DayNhaIsNotExistException();
        }

        dayNhaRepository.deleteById(dayNhaId);
    }

}

