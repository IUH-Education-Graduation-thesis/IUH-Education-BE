package com.hong_hoan.iuheducation.resolvers.response.account;

import com.hong_hoan.iuheducation.entity.SinhVien;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginData {
    private String token;
    private SinhVien sinhVien;
}
