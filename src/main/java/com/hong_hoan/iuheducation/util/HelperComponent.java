package com.hong_hoan.iuheducation.util;

import com.hong_hoan.iuheducation.entity.HocPhan;
import com.hong_hoan.iuheducation.entity.MonHoc;
import com.hong_hoan.iuheducation.entity.SinhVienLopHocPhan;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class HelperComponent {
    public static String byPaddingZeros(int value, int paddingLength) {
        return String.format("%0" + paddingLength + "d", value);
    }

    public List<Date> getDatesInWeek(Date refDate, int firstDayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(refDate);
        int _offset = calendar.get(Calendar.DAY_OF_WEEK) - 2;

        calendar.add(Calendar.DATE, -_offset);

        List<Date> dates = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static Double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static Double tinhDiemTrungBinhh(SinhVienLopHocPhan sinhVienLopHocPhan) {
        if(sinhVienLopHocPhan.getDiemCuoiKy() == null) {
            return null;
        }

        double _tongLyThuyet = (sinhVienLopHocPhan.getDiemThuongKy().stream().mapToDouble(a -> a).average().getAsDouble() * 20 + sinhVienLopHocPhan.getDiemGiuaKy() * 30 + sinhVienLopHocPhan.getDiemCuoiKy() * 50) / 100;

        MonHoc _monHoc = sinhVienLopHocPhan.getLopHocPhan().getHocPhan().getMonHoc();
        HocPhan _hocPhan = sinhVienLopHocPhan.getLopHocPhan().getHocPhan();

        if (_hocPhan.getSoTinChiThucHanh() <= 0) {
            return _tongLyThuyet;
        }

        return ((_tongLyThuyet * _hocPhan.getSoTinChiLyThuyet()) + (sinhVienLopHocPhan.getDiemThucHanh().stream().mapToDouble(a -> a).average().getAsDouble() * _hocPhan.getSoTinChiThucHanh())) / (_hocPhan.getSoTinChiLyThuyet() + _hocPhan.getSoTinChiThucHanh());
    }


    // Java 9
    public static void copyInputStreamToFileJava9(InputStream input, File file)
            throws IOException {

        // append = false
        try (OutputStream output = new FileOutputStream(file, false)) {
            input.transferTo(output);
        }

    }
}
