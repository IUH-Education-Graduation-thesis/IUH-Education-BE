package com.hong_hoan.iuheducation.resolvers.response.lop_hoc_phan;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class CheckLichHocRes {
    private Boolean isTrung;
    private List<NgayTrongTuan> listNgayTrongTuan;

}
