package com.hong_hoan.iuheducation.resolvers.mutation;

import com.hong_hoan.iuheducation.entity.Account;
import com.hong_hoan.iuheducation.entity.LopHocPhan;
import com.hong_hoan.iuheducation.entity.SinhVien;
import com.hong_hoan.iuheducation.entity.SinhVienLopHocPhan;
import com.hong_hoan.iuheducation.exception.*;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.DangKyHocPhanInputs;
import com.hong_hoan.iuheducation.resolvers.input.lop_hoc_phan.ThemLopHocPhanInputs;
import com.hong_hoan.iuheducation.resolvers.input.sinh_vien_lop_hoc_phan.SuaSinhVienLopHocPhanInputs;
import com.hong_hoan.iuheducation.resolvers.response.lop_hoc_phan.CheckLichHocRes;
import com.hong_hoan.iuheducation.resolvers.response.lop_hoc_phan.CheckLichTrungResponse;
import com.hong_hoan.iuheducation.resolvers.response.lop_hoc_phan.LopHocPhanResponse;
import com.hong_hoan.iuheducation.resolvers.response.sinh_vien.SinhVienResponse;
import com.hong_hoan.iuheducation.resolvers.response.sinh_vien_lop_hoc_phan.SinhVienLopHocPhanResponse;
import com.hong_hoan.iuheducation.service.AccountService;
import com.hong_hoan.iuheducation.service.LopHocPhanService;
import com.hong_hoan.iuheducation.service.SinhVienLopHocPhanService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LopHocPhanMutation implements GraphQLMutationResolver {
    @Autowired
    private LopHocPhanService lopHocPhanService;
    @Autowired
    private SinhVienLopHocPhanService sinhVienLopHocPhanService;
    @Autowired
    private AccountService accountService;

    /*
    lop hoc phan
    ======================================================================
 */
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public LopHocPhanResponse huyLopHocPhan(Long lopHocPhanId) {
        Account _account = accountService.getCurrentAccount();

        try {
            LopHocPhan _lopHocPhan = lopHocPhanService.huyLopHocPhan(lopHocPhanId, _account);

            return LopHocPhanResponse.builder().status(ResponseStatus.OK).message("H???y ????ng k?? l???p h???c ph???n th??nh c??ng!").data(Arrays.asList(_lopHocPhan)).build();
        } catch (LopHocPhanLockedException ex) {
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("H???y ????ng k?? l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("L???p h???c ph???n ???? kh??a, kh??ng th??? h???y!").build())).build();

        } catch (LopHocPhanIsNotExist ex) {
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("H???y ????ng k?? l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("L???p h???c ph???n kh??ng t???n t???i!").build())).build();
        } catch (SinhVienLopHocPhanIsNotExist ex) {
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("H???y ????ng k?? l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("Sinh vi??n ch??a ????ng k?? l???p h???c ph???n n??y!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("H???y ????ng k?? l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("L???i h??? th???ng!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public CheckLichTrungResponse checkLichTrung(List<DangKyHocPhanInputs> listLopHocPhanPrepareDangKy, Long hocKyNormalId) {
        try {
            Account _currentAccoutn = accountService.getCurrentAccount();
            CheckLichHocRes _checkLichHocRes = lopHocPhanService.checkLichHoc(listLopHocPhanPrepareDangKy, hocKyNormalId, _currentAccoutn);

            return CheckLichTrungResponse.builder().status(ResponseStatus.OK).message("Ki???m tram l???ch tr??ng th??nh c??ng.").data(Arrays.asList(_checkLichHocRes)).build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return CheckLichTrungResponse.builder().status(ResponseStatus.OK).message("Ki???m tram l???ch tr??ng th??nh c??ng.").errors(Arrays.asList(ErrorResponse.builder().message("L???i h??? th???ng!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SinhVienResponse xoaSinhVienOfLopHocPhan(List<Long> sinhVienIds, Long lopHocPhanId) {
        try {
            List<SinhVien> _listSinhVien = lopHocPhanService.xoaSinhVienOfLopHocPhan(sinhVienIds, lopHocPhanId);

            return SinhVienResponse.builder().status(ResponseStatus.OK).message("X??a sinh vi??n kh???i l???p h???c ph???n th??nh c??ng.").data(_listSinhVien).build();
        } catch (LopHocPhanIsNotExist ex) {
            return SinhVienResponse.builder().status(ResponseStatus.ERROR).message("X??a sinh vi??n kh???i l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("L???p h???c ph???n kh??ng t???n t???i!").build())).build();
        } catch (SinhVienLopHocPhanIsNotExist ex) {
            return SinhVienResponse.builder().status(ResponseStatus.ERROR).message("X??a sinh vi??n kh???i l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("Sinh vi??n kh??ng t???n t???i trong l???p h???c ph???n!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return SinhVienResponse.builder().status(ResponseStatus.ERROR).message("X??a sinh vi??n kh???i l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("L???i h??? th???ng!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public LopHocPhanResponse themLopHocPhan(ThemLopHocPhanInputs inputs) {
        try {
            LopHocPhan _lopHocPhan = lopHocPhanService.themLopHocPhan(inputs);

            return LopHocPhanResponse.builder().status(ResponseStatus.OK).message("Th??m l???p h???c ph???n th??nh c??ng!").data(Arrays.asList(_lopHocPhan)).build();
        } catch (HocPhanIsNotExist ex) {
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Th??m l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("H???c ph???n kh??ng t???n t???i!").build())).build();
        } catch (HocKyNormalIsNotExist ex) {
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Th??m l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("H???c k??? kh??ng t???n t???i!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Th??m l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("L???i h??? th???ng!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public LopHocPhanResponse suaLopHocPhan(ThemLopHocPhanInputs inputs, Long id) {
        try {
            LopHocPhan _lopHocPhan = lopHocPhanService.suaLopHocPhan(inputs, id);

            return LopHocPhanResponse.builder().status(ResponseStatus.OK).message("Th??m l???p h???c ph???n th??nh c??ng!").data(Arrays.asList(_lopHocPhan)).build();
        } catch (LopHocPhanIsNotExist ex) {
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Th??m l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("L???p h???c ph???n kh??ng t???n t???i!").build())).build();
        } catch (HocPhanIsNotExist ex) {
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Th??m l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("H???c ph???n kh??ng t???n t???i!").build())).build();
        } catch (HocKyNormalIsNotExist ex) {
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Th??m l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("H???c k??? kh??ng t???n t???i!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Th??m l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("L???i h??? th???ng!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SinhVienResponse themSinhVienVaoLopHocPhan(Long lopHocPhanId, Set<Long> sinhVienIds, Integer nhomThucHanh) {
        try {
            List<SinhVienLopHocPhan> _listSinhVienLopHocPhan = lopHocPhanService.themSinhVienVaoLopHocPhan(lopHocPhanId, sinhVienIds, nhomThucHanh);
            List<SinhVien> _listSinhVien = _listSinhVienLopHocPhan.stream().map(i -> i.getSinhVien()).collect(Collectors.toList());

            return SinhVienResponse.builder().status(ResponseStatus.OK).message("Th??m sinh vi??n v??o l???p h???c ph???n th??nh c??ng!").data(_listSinhVien).build();
        } catch (LopHocPhanIsNotExist ex) {
            return SinhVienResponse.builder().status(ResponseStatus.ERROR).message("Th??m sinh vi??n v??o l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("L???p h???c ph???n kh??ng t???n t???i!").build())).build();
        } catch (EnterNhomThucHanh ex) {
            return SinhVienResponse.builder().status(ResponseStatus.ERROR).message("Th??m sinh vi??n v??o l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("Ch??a ch???n nh??m th???c h??nh!").build())).build();
        } catch (ValueOver ex) {
            return SinhVienResponse.builder().status(ResponseStatus.ERROR).message("Th??m sinh vi??n v??o l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("Nh??m th???c h??nh kh??ng ????ng!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return SinhVienResponse.builder().status(ResponseStatus.ERROR).message("Th??m sinh vi??n v??o l???p h???c ph???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("L???i h??? th???ng!")

                    .build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TEACHER')")
    public SinhVienLopHocPhanResponse suaDiemSinhVien(SuaSinhVienLopHocPhanInputs inputs) {
        try {
            SinhVienLopHocPhan _sinhVienLopHocPhan = sinhVienLopHocPhanService.suaDiemSinhVien(inputs);

            return SinhVienLopHocPhanResponse.builder().status(ResponseStatus.OK).message("S???a ??i???m sinh vi??n th??nh c??ng!").data(Arrays.asList(_sinhVienLopHocPhan)).build();
        } catch (SinhVienLopHocPhanIsNotExist ex) {
            return SinhVienLopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("S???a ??i???m sinh vi??n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("Sinh vi??n l???p h???c ph???n kh??ng t???n t???i!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return SinhVienLopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("S???a ??i???m sinh vi??n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("L???i h??? th???ng!").build())).build();
        }
    }

}
