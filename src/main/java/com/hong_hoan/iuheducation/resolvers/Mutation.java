package com.hong_hoan.iuheducation.resolvers;

import com.hong_hoan.iuheducation.entity.*;
import com.hong_hoan.iuheducation.exception.*;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import com.hong_hoan.iuheducation.resolvers.input.CreateAccountInput;
import com.hong_hoan.iuheducation.resolvers.input.chuyen_nganh.ThemChuyenNganhInputs;
import com.hong_hoan.iuheducation.resolvers.input.day_nha.SuaDayNhaInput;
import com.hong_hoan.iuheducation.resolvers.input.day_nha.ThemDayNhaInput;
import com.hong_hoan.iuheducation.resolvers.input.giang_vien.ThemGiangVienInputs;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.DangKyHocPhanInputs;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.ThemHocPhanInputs;
import com.hong_hoan.iuheducation.resolvers.input.khoa_hoc.ThemKhoaHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.khoa_vien.ThemKhoaVienInputs;
import com.hong_hoan.iuheducation.resolvers.input.lop.ThemLopInputs;
import com.hong_hoan.iuheducation.resolvers.input.phong_hoc.ThemPhongHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.sinh_vien.SinhVienInputs;
import com.hong_hoan.iuheducation.resolvers.response.lop_hoc_phan.DangKyHocPhanResponse;
import com.hong_hoan.iuheducation.resolvers.response.chuyen_nganh.ChuyenNganhResponse;
import com.hong_hoan.iuheducation.resolvers.response.giang_vien.GiangVienResponse;
import com.hong_hoan.iuheducation.resolvers.response.hoc_phan.HocPhanResponse;
import com.hong_hoan.iuheducation.resolvers.response.khoa_hoc.KhoaHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.khoa_vien.KhoaVienResponse;
import com.hong_hoan.iuheducation.resolvers.response.lop.LopResponse;
import com.hong_hoan.iuheducation.resolvers.response.account.LoginData;
import com.hong_hoan.iuheducation.resolvers.response.account.LoginResponse;
import com.hong_hoan.iuheducation.resolvers.response.account.RegisterResponse;
import com.hong_hoan.iuheducation.resolvers.response.day_nha.DayNhaResponse;
import com.hong_hoan.iuheducation.resolvers.response.lop_hoc_phan.HocPhanDangKy;
import com.hong_hoan.iuheducation.resolvers.response.notification.NotificationResponse;
import com.hong_hoan.iuheducation.resolvers.response.phong_hoc.PhongHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.sinh_vien.SinhVienResponse;
import com.hong_hoan.iuheducation.resolvers.response.sinh_vien.SuccessAndFailSinhVien;
import com.hong_hoan.iuheducation.resolvers.response.sinh_vien.ThemSinhVienWithFileResponse;
import com.hong_hoan.iuheducation.service.*;
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.Part;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class Mutation implements GraphQLMutationResolver {


    @Autowired
    private DayNhaService dayNhaService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private PhongHocService phongHocService;
    @Autowired
    private KhoaHocService khoaHocService;
    @Autowired
    private HocKyService hocKyService;
    @Autowired
    private LichHocService lichHocService;
    @Autowired
    private KhoaVienService khoaVienService;
    @Autowired
    private ChuyenNganhService chuyenNganhService;
    @Autowired
    private MonHocService monHocService;
    @Autowired
    private GiangVienService giangVienService;
    @Autowired
    private SinhVienService sinhVienService;
    @Autowired
    private SinhVienLopHocPhanService sinhVienLopHocPhanService;
    @Autowired
    private LopService lopService;
    @Autowired
    private HocPhanService hocPhanService;
    @Autowired
    private LopHocPhanService lopHocPhanService;
    @Autowired
    private NotificationService notificationService;


    @PreAuthorize("isAuthenticated()")
    public NotificationResponse suaNotification(Long id, boolean isRead) {
        try {
            Notification _notification = notificationService.suaNotification(id, isRead);

            return NotificationResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("S???a th??ng b??o th??nh c??ng.")
                    .data(Arrays.asList(_notification))
                    .build();
        } catch (NotificationIsNotExist ex) {
            return NotificationResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a th??ng b??o kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Th??ng b??o kh??ng t???n t???i!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return NotificationResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a th??ng b??o kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???i h??? th???ng!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public DangKyHocPhanResponse dangKyHocPhan(List<DangKyHocPhanInputs> inputs) {
        Account _account = accountService.getCurrentAccount();

        try {
            HocPhanDangKy _hocPhanDangKy = sinhVienLopHocPhanService.themSinhVienLopHocPhan(inputs, _account);

            return DangKyHocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("????ng k?? h???c ph???n th??nh c??ng.")
                    .data(Arrays.asList(_hocPhanDangKy))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return DangKyHocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("????ng k?? h???c ph???n Kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???i h??? th???ng!")
                            .build()))
                    .build();
        }

    }

    /*
     * Sinh vien
     * ============================================================================
     * */

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SinhVienResponse suaSinhVien(SinhVienInputs inputs, Long sinhVienId) {
        try {
            SinhVien _sinhVien = sinhVienService.suaSinhVien(inputs, sinhVienId);

            return SinhVienResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(Arrays.asList(_sinhVien))
                    .message("S???a sinh vi??n th??nh c??ng.")
                    .build();
        } catch (SinhVienIsNotExist ex) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a sinh vi??n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Kh??ng t??m th???y sinh vi??n!")
                            .build()))
                    .build();

        } catch (LopIsNotExist e) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a sinh vi??n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Kh??ng t??m th???y l???p h???c!")
                            .build()))
                    .build();

        } catch (Exception ex) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a sinh vi??n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???i h??? th???ng!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SinhVienResponse xoaSinhViens(List<Long> ids) {
        try {
            List<SinhVien> _listSinhVien = sinhVienService.xoaSinhViens(ids);

            return SinhVienResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("X??a sinh vi??n th??nh c??ng!")
                    .data(_listSinhVien)
                    .build();
        } catch (SinhVienIsNotExist ex) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("X??a sinh vi??n kh??ng th??nh c??ng!")
                    .errors(Arrays.asList(
                            ErrorResponse.builder()
                                    .message("Sinh vi??n kh??ng t???n t???i!")
                                    .build()
                    ))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("X??a sinh vi??n kh??ng th??nh c??ng!")
                    .errors(Arrays.asList(
                            ErrorResponse.builder()
                                    .message("L???i h??? th???ng!")
                                    .build()
                    ))
                    .build();
        }


    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ThemSinhVienWithFileResponse themSinhViens(List<Part> files, Long lopId, DataFetchingEnvironment env) {
        DefaultGraphQLServletContext _context = env.getContext();

        try {
            Part _part = _context.getFileParts().get(0);
            SuccessAndFailSinhVien _successAndFailSinhVien = sinhVienService.addSinhVienWithFile(_part, lopId);
            return ThemSinhVienWithFileResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Th??m sinh vi??n th??nh c??ng.")
                    .data(Arrays.asList(_successAndFailSinhVien))
                    .build();
        } catch (IndexOutOfBoundsException ex) {
            return ThemSinhVienWithFileResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Th??m sinh vi??n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Kh??ng t??m th???y file ???????c th??m!")
                            .build()))
                    .build();
        } catch (LopIsNotExist e) {
            return ThemSinhVienWithFileResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Th??m sinh vi??n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???p kh??ng t???n t???i!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ThemSinhVienWithFileResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Th??m sinh vi??n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???i h??? th???ng!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SinhVienResponse themSinhVien(SinhVienInputs inputs) {
        try {
            SinhVien _sinhVien = sinhVienService.addSinhVien(inputs);

            return SinhVienResponse.builder().status(ResponseStatus.OK).message("Th??m sinh vi??n th??nh c??ng.").data(Arrays.asList(_sinhVien)).build();

        } catch (NumberFormatException ex) {
            return SinhVienResponse.builder().status(ResponseStatus.OK).message("Th??m sinh vi??n kh??ng th??nh c??ng.").errors(Arrays.asList(ErrorResponse.builder().message("L???p h???c kh??ng t???n t???i!").error_fields(Arrays.asList("lopId")).build())).build();
        } catch (LopIsNotExist ex) {
            return SinhVienResponse.builder().status(ResponseStatus.OK).message("Th??m sinh vi??n kh??ng th??nh c??ng.").errors(Arrays.asList(ErrorResponse.builder().message("L???p h???c kh??ng t???n t???i!").error_fields(Arrays.asList("lopId")).build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return SinhVienResponse.builder().status(ResponseStatus.OK).message("Th??m sinh vi??n kh??ng th??nh c??ng.").errors(Arrays.asList(ErrorResponse.builder().message("L???i h??? th???ng!").build())).build();
        }
    }

    /*
        hoc phan
        ======================================================================
     */

    public HocPhanResponse themHocPhan(ThemHocPhanInputs inputs) {
        try {
            HocPhan _hocPhan = hocPhanService.themHocPhan(inputs);

            return HocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Th??m h???c ph???n th??nh c??ng.")
                    .data(Arrays.asList(_hocPhan))
                    .build();
        } catch (MonHocIsExistException ex) {
            return HocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Th??m h???c ph???n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("M??n h???c kh??ng t???n t???i!")
                            .build()))
                    .build();
        } catch (HocKyIsNotExist ex) {
            return HocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Th??m h???c ph???n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("H???c k??? kh??ng t???n t???i!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return HocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Th??m h???c ph???n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???i h??? th???ng!")
                            .build()))
                    .build();
        }


    }

    public HocPhanResponse suaHocPhan(ThemHocPhanInputs inputs, Long id) {
        try {
            HocPhan _hocPhan = hocPhanService.suaHocPhan(inputs, id);

            return HocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("S???a h???c ph???n th??nh c??ng.")
                    .data(Arrays.asList(_hocPhan))
                    .build();
        } catch (MonHocIsExistException ex) {
            return HocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a h???c ph???n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("M??n h???c kh??ng t???n t???i!")
                            .build()))
                    .build();
        } catch (HocKyIsNotExist ex) {
            return HocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a h???c ph???n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("H???c k??? kh??ng t???n t???i!")
                            .build()))
                    .build();
        } catch (HocPhanIsNotExist ex) {
            return HocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a h???c ph???n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("H???c ph???n kh??ng t???n t???i!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            return HocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a h???c ph???n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???i h??? th???ng!")
                            .build()))
                    .build();
        }
    }

    public HocPhanResponse xoaHocPhans(List<Long> ids) {
        try {
            List<HocPhan> _hocPhans = hocPhanService.xoaHocPhans(ids);

            return HocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("X??a h???c ph???n th??nh c??ng.")
                    .data(_hocPhans)
                    .build();
        } catch (HocPhanIsNotExist ex) {
            return HocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("X??a h???c ph???n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("H???c ph???n kh??ng t???n t???i!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            return HocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("X??a h???c ph???n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???i h??? th???ng!")
                            .build()))
                    .build();
        }
    }

     /*
        lop
        ======================================================================
     */

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public LopResponse xoaLops(List<Long> ids) {
        try {
            List<Lop> _lops = lopService.xoaLops(ids);

            return LopResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("X??a l???p th??nh c??ng.")
                    .data(_lops)
                    .build();
        } catch (LopIsNotExist e) {
            return LopResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("X??a l???p kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???p h???c kh??ng t???n t???i!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            return LopResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("X??a l???p kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???i h??? th???ng!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public LopResponse themLop(ThemLopInputs inputs) {
        try {
            Lop _lop = lopService.themLop(inputs);

            return LopResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Th??m l???p th??nh c??ng.")
                    .data(Arrays.asList(_lop))
                    .build();
        } catch (KhoaHocIsNotExist ex) {
            return LopResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Th??m l???p kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Kh??a h???c kh??ng t???n t???i!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            return LopResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Th??m l???p kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???i h??? th???ng!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public LopResponse suaLop(ThemLopInputs inputs, Long id) {
        try {
            Lop _lop = lopService.suaLop(inputs, id);

            return LopResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("S???a l???p th??nh c??ng.")
                    .data(Arrays.asList(_lop))
                    .build();
        } catch (LopIsNotExist ex) {
            return LopResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a l???p kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???p h???c kh??ng t???n t???i!")
                            .build()))
                    .build();
        } catch (KhoaHocIsNotExist ex) {
            return LopResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a l???p kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Kh??a h???c kh??ng t???n t???i!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            return LopResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a l???p kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???i h??? th???ng!")
                            .build()))
                    .build();
        }
    }


    /*
        giang vien
        ======================================================================
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public GiangVienResponse suaGiangVien(ThemGiangVienInputs inputs, Long id) {
        try {
            GiangVien _giangVien = giangVienService.suaGiangVien(inputs, id);

            return GiangVienResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("S???a gi???ng vi??n th??nh c??ng.")
                    .data(Arrays.asList(_giangVien))
                    .build();
        } catch (GiangVienIsNotExistException ex) {

            return GiangVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a gi???ng vi??n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Gi???ng vi??n kh??ng t???n t???i!")
                            .build()))
                    .build();

        } catch (ChuyenNganhIsNotExistExcepton ex) {
            return GiangVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a gi???ng vi??n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Chuy??n ng??nh kh??ng t???n t???i!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            return GiangVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a gi???ng vi??n kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???i h??? th???ng!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public GiangVienResponse themGiangVien(ThemGiangVienInputs inputs) {
        try {
            GiangVien _giangVienInput = giangVienService.themGiangVien(inputs);
            return GiangVienResponse.builder().status(ResponseStatus.OK).message("Th??m gi???ng vi??n th??nh c??ng!").data(List.of(_giangVienInput)).build();
        } catch (ChuyenNganhIsNotExistExcepton ex) {
            return GiangVienResponse.builder().status(ResponseStatus.ERROR).message("Th??m gi???ng vi??n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("chuyenNganhID")).message("Chuy??n ng??nh kh??ng t???n t???i!").build())).build();
        } catch (Exception ex) {
            return GiangVienResponse.builder().status(ResponseStatus.ERROR).message("Th??m gi???ng vi??n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("L???i h??? th???ng!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public GiangVienResponse xoaGiangViens(Set<Long> ids) {
        try {
            List<GiangVien> _idGiangVien = giangVienService.xoaGiangViens(ids);
            return GiangVienResponse.builder().status(ResponseStatus.OK).message("X??a gi???ng vi??n th??nh c??ng").data(_idGiangVien).build();
        } catch (Exception exception) {
            return GiangVienResponse.builder().status(ResponseStatus.ERROR).message("X??a gi???ng vi??n kh??ng th??nh c??ng").errors(Arrays.asList(ErrorResponse.builder().message("Gi???ng vi??n kh??ng t???n t???i!").build())).build();
        }
    }

    /*
        chuyen nganh
        ======================================================================
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ChuyenNganhResponse suaChuyenNganh(ThemChuyenNganhInputs inputs, Long id) {
        try {
            ChuyenNganh _chuyenNganh = chuyenNganhService.suaChuyenNganh(inputs, id);

            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("S???a chuy??n ng??nh th??nh c??ng.")
                    .data(Arrays.asList(_chuyenNganh))
                    .build();
        } catch (KhoaVienIsNotExistException ex) {
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("S???a chuy??n ng??nh kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Khoa vi???n kh??ng t???n t???i.")
                            .build()))
                    .build();
        } catch (ChuyenNganhIsNotExistExcepton ex) {
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("S???a chuy??n ng??nh kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Chuy??n ng??nh kh??ng t???n t???i.")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("S???a chuy??n ng??nh kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???i h??? th???ng.")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ChuyenNganhResponse themChuyenNganh(ThemChuyenNganhInputs inputs) {
        System.out.println(inputs);
        try {
            ChuyenNganh _chuyenNganhInput = chuyenNganhService.themChuyenNganh(inputs);
            return ChuyenNganhResponse.builder().status(ResponseStatus.OK).message("Th??m chuy??n ng??nh th??nh c??ng!").data(List.of(_chuyenNganhInput)).build();
        } catch (KhoaVienIsNotExistException ex) {
            return ChuyenNganhResponse.builder().status(ResponseStatus.ERROR).message("Th??m chuy??n ng??nh kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("khoaVienID")).message("Khoa vi???n kh??ng t???n t???i!").build())).build();
        } catch (Exception ex) {
            return ChuyenNganhResponse.builder().status(ResponseStatus.ERROR).message("Th??m chuy??n ng??nh kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("L???i h??? th???ng!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ChuyenNganhResponse xoaChuyenNganhs(Set<Long> ids) {
        try {
            List<ChuyenNganh> _idChuyenNganh = chuyenNganhService.xoaChuyenNganhs(ids);
            return ChuyenNganhResponse.builder().status(ResponseStatus.OK).message("X??a chuy??n ng??nh th??nh c??ng").data(_idChuyenNganh).build();
        } catch (Exception exception) {
            return ChuyenNganhResponse.builder().status(ResponseStatus.ERROR).message("X??a chuy??n ng??nh kh??ng th??nh c??ng").errors(Arrays.asList(ErrorResponse.builder().message("Chuy??n ng??nh kh??ng t???n t???i!").build())).build();
        }
    }
    /*
        khoa vien
        ======================================================================
     */

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public KhoaVienResponse suaKhoaVien(ThemKhoaVienInputs inputs, Long id) {
        try {
            KhoaVien _khoaVien = khoaVienService.suaKhoaVien(inputs, id);

            return KhoaVienResponse.builder()
                    .message("Sua khoa vien thanh cong!")
                    .status(ResponseStatus.OK)
                    .data(Arrays.asList(_khoaVien))
                    .build();
        } catch (Exception ex) {
            return KhoaVienResponse.builder()
                    .message("S???a khoa vi???n kh??ng th??nh c??ng!")
                    .status(ResponseStatus.ERROR)
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???i h??? th???ng!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public KhoaVienResponse themKhoaVien(ThemKhoaVienInputs inputs) {
        try {
            KhoaVien _khoaVienInput = khoaVienService.themKhoaVien(inputs);
            return KhoaVienResponse.builder().status(ResponseStatus.OK).message("Th??m khoa vi???n th??nh c??ng").data(List.of(_khoaVienInput)).build();
        } catch (Exception ex) {
            return KhoaVienResponse.builder().status(ResponseStatus.ERROR).message("Th??m khoa vi???n kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("L???i h??? th???ng!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public KhoaVienResponse xoaKhoaViens(Set<Long> ids) {
        try {
            List<KhoaVien> _idKhoaVien = khoaVienService.xoaKhoaViens(ids);
            return KhoaVienResponse.builder().status(ResponseStatus.OK).message("X??a khoa vi???n th??nh c??ng").data(_idKhoaVien).build();
        } catch (Exception exception) {
            return KhoaVienResponse.builder().status(ResponseStatus.ERROR).message("X??a khoa vi???n kh??ng th??nh c??ng").errors(Arrays.asList(ErrorResponse.builder().message("Khoa vi???n kh??ng t???n t???i!").build())).build();
        }
    }

    /*
        phong hoc
        ======================================================================
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public KhoaHocResponse suaKhoaHoc(ThemKhoaHocInputs inputs, Long id) {
        try {
            Khoa _khoa = khoaHocService.suaKhoaHoc(inputs, id);

            return KhoaHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("S???a kh??a h???c th??nh c??ng.")
                    .data(Arrays.asList(_khoa))
                    .build();
        } catch (KhoaHocIsNotExist ex) {
            return KhoaHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a kh??a h???c kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Kh??a h???c kh??ng t???n t???i!")
                            .build()))
                    .build();
        } catch (ChuyenNganhIsNotExistExcepton ex) {
            return KhoaHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a kh??a h???c kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("chuy??n ng??nh kh??ng t???n t???i!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            return KhoaHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("S???a kh??a h???c kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("L???i h??? th???ng!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public KhoaHocResponse themKhoaHoc(ThemKhoaHocInputs inputs) {

        try {
            Khoa _khoa = khoaHocService.createKhoaHoc(inputs);

            return KhoaHocResponse.builder().status(ResponseStatus.OK).message("Th??m kh??a h???c th??nh c??ng.").data(Arrays.asList(_khoa)).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return KhoaHocResponse.builder().status(ResponseStatus.ERROR).message("Th??m kh??a h???c kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("L???i h??? th???ng!").build())).build();
        }


    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public KhoaHocResponse xoaKhoaHocs(List<Long> ids) {
        try {
            List<Khoa> _listKhoa = khoaHocService.deleteKhoaHoc(ids);

            return KhoaHocResponse.builder().status(ResponseStatus.OK)
                    .data(_listKhoa)
                    .message("X??a kh??a h???c th??nh c??ng.").build();

        } catch (NumberFormatException ex) {
            return KhoaHocResponse.builder().status(ResponseStatus.ERROR).message("X??a kh??a h???c kh??ng th??nh c??ng.").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("id")).message("Id nh???p kh??ng ????ng format!").build())).build();

        } catch (KhoaHocIsNotExist ex) {
            return KhoaHocResponse.builder().status(ResponseStatus.ERROR).message("X??a kh??a h???c kh??ng th??nh c??ng.").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("id")).message("Kh??a h???c kh??ng t???n t???i").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return KhoaHocResponse.builder().status(ResponseStatus.ERROR).message("X??a kh??a h???c kh??ng th??nh c??ng.").errors(Arrays.asList(ErrorResponse.builder().message("L???i h??? th???ng!").build())).build();
        }
    }

    /*
        phong hoc
        ======================================================================
     */

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public PhongHocResponse xoaPhongHoc(String id) {
        try {
            long _id = Long.valueOf(id);

            phongHocService.xoaPhongHoc(_id);

            return PhongHocResponse.builder().status(ResponseStatus.OK).message("X??a ph??ng h???c th??nh c??ng.").build();
        } catch (PhongHocIsNotExist ex) {
            return PhongHocResponse.builder().status(ResponseStatus.ERROR).message("X??a ph??ng h???c kh??ng th??nh c??ng.").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("id")).message("Ph??ng h???c kh??ng t???n t???i!").build())).build();
        } catch (NumberFormatException ex) {
            return PhongHocResponse.builder().status(ResponseStatus.ERROR).message("X??a ph??ng h???c kh??ng th??nh c??ng.").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("id")).message("Ph??ng hoc ID kh??ng ????ng format!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public PhongHocResponse themPhongHoc(ThemPhongHocInputs inputs) {

        try {
            PhongHoc _phongHoc = phongHocService.themPhongHoc(inputs);

            return PhongHocResponse.builder().status(ResponseStatus.OK).message("Th??m ph??ng h???c th??nh c??ng.").data(Arrays.asList(_phongHoc)).build();
        } catch (DayNhaIsNotExistException ex) {
            return PhongHocResponse.builder().status(ResponseStatus.ERROR).message("Th??m ph??ng h???c kh??ng th??nh c??ng.").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("dayNhaId")).message("D??y nh?? kh??ng t???n t???i!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return PhongHocResponse.builder().status(ResponseStatus.ERROR).message("Th??m ph??ng h???c kh??ng th??nh c??ng.").errors(Arrays.asList(ErrorResponse.builder().message("L???i h??? th???ng!").build())).build();
        }

    }

    /*
        day nha
        ======================================================================
     */

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DayNhaResponse xoaDayNha(Set<Long> ids) {
        try {
            List<DayNha> _dayNhaDeleted = dayNhaService.deleteDayNhas(ids);

            return DayNhaResponse.builder().status(ResponseStatus.OK).message("X??a d??y nh?? th??nh c??ng.").data(_dayNhaDeleted).build();
        } catch (DayNhaIsNotExistException ex) {
            return DayNhaResponse.builder().status(ResponseStatus.ERROR).message("X??a d??y nh?? kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("D??y nh?? kh??ng t???n t???i!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DayNhaResponse suaDayNha(SuaDayNhaInput inputs) {


        DayNha _dayNhaInput = DayNha.builder().id(inputs.getId()).tenDayNha(inputs.getTenDayNha()).moTa(inputs.getMoTa()).build();

        try {
            DayNha _dayNhaResponse = dayNhaService.updateDayNha(_dayNhaInput);

            return DayNhaResponse.builder().status(ResponseStatus.OK).message("S???a d??y nh?? th??nh c??ng!").data(Arrays.asList(_dayNhaResponse)).build();
        } catch (DayNhaIsNotExistException ex) {
            return DayNhaResponse.builder().status(ResponseStatus.ERROR).message("S???a d??y nh?? kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("D??y nh?? kh??ng t???n t???i trong h??? th???ng!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return DayNhaResponse.builder().status(ResponseStatus.ERROR).message("S???a d??y nh?? kh??ng th??nh c??ng!").errors(Arrays.asList(ErrorResponse.builder().message("L???i h??? th???ng!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DayNhaResponse themDayNha(ThemDayNhaInput inputs) {
        DayNha _dayNhaInput = DayNha.builder().tenDayNha(inputs.getTenDayNha()).moTa(inputs.getMoTa()).build();

        DayNha _dayNhaResponse = dayNhaService.themDayNha(_dayNhaInput);

        return DayNhaResponse.builder().status(ResponseStatus.OK).message("Th??m d??y nh?? th??nh c??ng!").data(Arrays.asList(_dayNhaResponse)).build();
    }

    /*
        login register
        ======================================================================
     */

    @PreAuthorize("isAnonymous()")
    public LoginResponse login(String user_name, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user_name, password);

        try {
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(authenticationToken));

            Account _account = accountService.getCurrentAccount();
            SinhVien _sinhVien = _account.getSinhVien();
            String _token = accountService.getToken(_account);

            LoginData _loginData = LoginData.builder().token(_token).sinhVien(_sinhVien).build();

            return LoginResponse.builder().data(_loginData).status(ResponseStatus.OK).message("????ng nh???p th??nh c??ng.").build();
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            return LoginResponse.builder().status(ResponseStatus.ERROR).message("????ng nh???p kh??ng th??nh c??ng!").errors(new ArrayList<>() {
                {
                    add(ErrorResponse.builder().message("T??n t??i kho???n ho???c m???t kh???u kh??ng ????ng!").build());
                }
            }).build();
        }

    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public RegisterResponse register(CreateAccountInput inputs) {
        try {
            Account _account = accountService.createAccount(null, inputs);

            return RegisterResponse.builder().status(ResponseStatus.OK).message("T???o t??i kho???n th??nh c??ng!").data(_account).build();
        } catch (UserAlreadyExistsException UAEexp) {
            return RegisterResponse.builder().status(ResponseStatus.ERROR).message("T???o t??i kho???n kh??ng th??nh c??ng!").errors(new ArrayList<>() {{
                add(ErrorResponse.builder().message("T??n t??i kho???n ???? t???n t???i!").error_fields(new ArrayList<>(Arrays.asList("username"))).build());
            }}).build();
        } catch (Exception exp) {
            return RegisterResponse.builder().status(ResponseStatus.ERROR).message("T???o t??i kho???n kh??ng th??nh c??ng!").errors(new ArrayList<>() {{
                add(ErrorResponse.builder().message("T??n t??i kho???n ???? t???n t???i!").error_fields(new ArrayList<>(Arrays.asList("username"))).build());
            }}).build();
        }
    }
}
