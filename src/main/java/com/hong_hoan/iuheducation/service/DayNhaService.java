package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.DayNha;
import com.hong_hoan.iuheducation.repository.DayNhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DayNhaService {
    @Autowired
    private DayNhaRepository dayNhaRepository;

    public List<DayNha> getListDayNha() {
        List<DayNha> _dayNhas = dayNhaRepository.findAll();

        return _dayNhas;
    }

    public DayNha themDayNha(DayNha dayNha) {
        DayNha _dayNha = dayNhaRepository.saveAndFlush(dayNha);
        return _dayNha;
    }

    public DayNha updateDayNha(DayNha dayNha) {
        DayNha _dayNha = dayNhaRepository.save(dayNha);

        return _dayNha;
    }

    public boolean deleteDayNha(long dayNhaId) {
        dayNhaRepository.deleteById(dayNhaId);

        boolean _isExist = dayNhaRepository.existsById(dayNhaId);

        return !_isExist;
    }

}
