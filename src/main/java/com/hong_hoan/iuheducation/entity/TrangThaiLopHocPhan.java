package com.hong_hoan.iuheducation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TrangThaiLopHocPhan {
    CHO_SINH_VIEN_DANG_KY("Chờ sinh viên đăng ký"),
    DANG_LEN_KE_HOACH("Đang lên kế hoạch"),
    CHAP_NHAN_MO_LOP("Chấp nhận mở lớp"),
    HUY_LOP_HOC_PHAN("Huy lớp hoc phần"),
    DA_KHOA("Đã khóa");

    private final String name;
}
