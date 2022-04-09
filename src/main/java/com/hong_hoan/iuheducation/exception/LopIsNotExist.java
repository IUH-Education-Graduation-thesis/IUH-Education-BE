package com.hong_hoan.iuheducation.exception;

import com.hong_hoan.iuheducation.entity.SinhVien;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LopIsNotExist extends Throwable {
    private SinhVien _sinhVien;

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
