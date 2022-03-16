package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.DayNha;
import com.hong_hoan.iuheducation.exception.DayNhaIsNotExistException;
import com.hong_hoan.iuheducation.repository.DayNhaRepository;
import com.hong_hoan.iuheducation.util.Helper;
import com.hong_hoan.iuheducation.util.Merge;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DayNhaService {
    private Helper helper = Helper.getInstance();

    @Autowired
    private DayNhaRepository dayNhaRepository;
    @Autowired
    private Merge merge;

    public List<DayNha> getListDayNha() {
        List<DayNha> _dayNhas = dayNhaRepository.findAll();

        return _dayNhas;
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

