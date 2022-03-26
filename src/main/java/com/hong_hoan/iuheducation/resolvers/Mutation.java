package com.hong_hoan.iuheducation.resolvers;

import com.hong_hoan.iuheducation.entity.*;
import com.hong_hoan.iuheducation.exception.*;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import com.hong_hoan.iuheducation.resolvers.input.CreateAccountInput;
import com.hong_hoan.iuheducation.resolvers.input.chuyen_nganh.ThemChuyenNganhInputs;
import com.hong_hoan.iuheducation.resolvers.input.day_nha.SuaDayNhaInput;
import com.hong_hoan.iuheducation.resolvers.input.day_nha.ThemDayNhaInput;
import com.hong_hoan.iuheducation.resolvers.input.hoc_ky.ThemHocKyInputs;
import com.hong_hoan.iuheducation.resolvers.input.khoa_hoc.ThemKhoaHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.khoa_vien.ThemKhoaVienInputs;
import com.hong_hoan.iuheducation.resolvers.input.lich_hoc.ThemLichHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.nam_hoc.ThemNamHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.phong_hoc.ThemPhongHocInputs;
import com.hong_hoan.iuheducation.resolvers.response.HocKyResponse;
import com.hong_hoan.iuheducation.resolvers.response.chuyen_nganh.ChuyenNganhResponse;
import com.hong_hoan.iuheducation.resolvers.response.khoa_hoc.KhoaHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.khoa_vien.KhoaVienResponse;
import com.hong_hoan.iuheducation.resolvers.response.nam_hoc.NamHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.account.LoginData;
import com.hong_hoan.iuheducation.resolvers.response.account.LoginResponse;
import com.hong_hoan.iuheducation.resolvers.response.account.RegisterResponse;
import com.hong_hoan.iuheducation.resolvers.response.day_nha.DayNhaResponse;
import com.hong_hoan.iuheducation.resolvers.response.lich_hoc.LichHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.phong_hoc.PhongHocResponse;
import com.hong_hoan.iuheducation.service.*;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
    private NamHocService namHocService;
    @Autowired
    private HocKyService hocKyService;
    @Autowired
    private LichHocService lichHocService;
    @Autowired
    private KhoaVienSevice khoaVienSevice;
    @Autowired
    private ChuyenNganhService chuyenNganhService;

    /*
        chuyen nganh
        ======================================================================
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ChuyenNganhResponse themChuyenNganh(ThemChuyenNganhInputs inputs){
        try{
            ChuyenNganh _chuyenNganhInput = chuyenNganhService.themChuyenNganh(inputs);
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Thêm chuyên ngành thành công!")
                    .data(List.of(_chuyenNganhInput))
                    .build();
        }
        catch (KhoaVienIsNotExistException ex){
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm chuyên ngành không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                                    .error_fields(Arrays.asList("khoaVienID"))
                            .message("Khoa viện không tồn tại!")
                            .build()))
                    .build();
        }
        catch (Exception ex){
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm chuyên ngành không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ChuyenNganhResponse xoaChuyenNganhs(Set<Long> ids){
        try{
            List<ChuyenNganh> _idChuyenNganh = chuyenNganhService.xoaChuyenNganhs(ids);
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xóa chuyên ngành thành công")
                    .data(_idChuyenNganh)
                    .build();
        }catch (Exception exception){
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa chuyên ngành không thành công")
                    .errors(Arrays.asList(ErrorResponse.builder().message("Chuyên ngành không tồn tại!").build()))
                    .build();
        }
    }
    /*
        khoa vien
        ======================================================================
     */

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public KhoaVienResponse themKhoaVien(ThemKhoaVienInputs inputs){
        try{
            KhoaVien _khoaVienInput = khoaVienSevice.themKhoaVien(inputs);
            return KhoaVienResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Thêm khoa viện thành công")
                    .data(List.of(_khoaVienInput))
                    .build();
        }catch(Exception ex){
            return KhoaVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm khoa viện không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public KhoaVienResponse xoaKhoaViens(Set<Long> ids){
        try{
            List<KhoaVien> _idKhoaVien = khoaVienSevice.xoaKhoaViens(ids);
            return KhoaVienResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xóa khoa viện thành công")
                    .data(_idKhoaVien)
                    .build();
        }catch (Exception exception){
            return KhoaVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa khoa viện không thành công")
                    .errors(Arrays.asList(ErrorResponse.builder().message("Khoa viện không tồn tại!").build()))
                    .build();
        }
    }
    /*
        lich hoc
        ======================================================================
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public LichHocResponse themLichHoc(ThemLichHocInputs inputs){
        try {
            LichHoc _lichHoc = lichHocService.themLichHoc(inputs);

            return LichHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Thêm lịch học thành công")
                    .data(Arrays.asList(_lichHoc))
                    .build();
        }
        catch (PhongHocIsNotExist ex){
            return  LichHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm lịch học không thành công")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Phòng học không tồn tại")
                            .build()))
                    .build();
        }
        catch (Exception ex){
            return LichHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm lịch học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }
    /*
        hoc ky
        ======================================================================
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public HocKyResponse xoaHocKy(String id) {
        try {
            hocKyService.xoaHocKy(id);
            return HocKyResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xóa học kỳ thành công.")
                    .build();
        } catch (NumberFormatException ex) {
            return HocKyResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa học kỳ không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .error_fields(Arrays.asList("id"))
                            .message("Hoc ky không tồn tại!")
                            .build()))
                    .build();
        } catch (HocKyIsNotExist ex) {
            return HocKyResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa học kỳ không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .error_fields(Arrays.asList("id"))
                            .message("Hoc ky không tồn tại!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return HocKyResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa học kỳ không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public HocKyResponse themHocKy(ThemHocKyInputs inputs) {
        try {
            HocKy _hocKy = hocKyService.themHocKy(inputs);

            return HocKyResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Thêm học kỳ thành công!")
                    .data(Arrays.asList(_hocKy))
                    .build();
        } catch (NumberFormatException ex) {
            return HocKyResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm học kỳ không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .error_fields(Arrays.asList("namHocId"))
                            .message("Năm học không tồn tại!")
                            .build()))
                    .build();
        } catch (NamHocIsNotExist ex) {
            return HocKyResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm học kỳ không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .error_fields(Arrays.asList("namHocId"))
                            .message("Năm học không tồn tại!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            return HocKyResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm học kỳ không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    /*
        nam hoc
        ======================================================================
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public NamHocResponse xoaNamHoc(String id) {
        try {
            namHocService.xoaNamHocById(id);

            return NamHocResponse.builder()
                    .message("Xóa năm học thành công!")
                    .status(ResponseStatus.OK)
                    .build();
        } catch (NumberFormatException ex) {
            return NamHocResponse.builder()
                    .message("Xóa năm học không thành công!")
                    .status(ResponseStatus.ERROR)
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .error_fields(Arrays.asList("id"))
                            .message("id nhập không đúng format!")
                            .build()))
                    .build();
        } catch (NamHocIsNotExist ex) {
            return NamHocResponse.builder()
                    .message("Xóa năm học không thành công!")
                    .status(ResponseStatus.ERROR)
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .error_fields(Arrays.asList("id"))
                            .message("Năm học không tồn tại!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return NamHocResponse.builder()
                    .message("Xóa năm học không thành công!")
                    .status(ResponseStatus.ERROR)
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public NamHocResponse themNamHoc(ThemNamHocInputs inputs) {
        try {
            NamHoc _namHoc = namHocService.themNamHoc(inputs);

            return NamHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Thêm năm học thành công.")
                    .data(Arrays.asList(_namHoc))
                    .build();
        } catch (NgayBatDauSauNgayKetThucException ex) {
            return NamHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm năm học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .error_fields(Arrays.asList("ngayBatDau"))
                            .message("Ngày bắt đầu nhỏ hơn ngày kết thúc!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return NamHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm năm học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }

    }

    /*
        phong hoc
        ======================================================================
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public KhoaHocResponse themKhoaHoc(ThemKhoaHocInputs inputs) {

        try {
            Khoa _khoa = khoaHocService.createKhoaHoc(inputs);

            return KhoaHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Thêm khóa học thành công.")
                    .data(Arrays.asList(_khoa))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return KhoaHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm khóa học không thành công!")
                    .errors(Arrays.asList(
                            ErrorResponse.builder()
                                    .message("Lỗi hệ thống!")
                                    .build()
                    ))
                    .build();
        }


    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public KhoaHocResponse xoaKhoaHoc(String id) {
        try {
            khoaHocService.deleteKhoaHoc(id);

            return KhoaHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xóa khóa học thành công.")
                    .build();

        } catch (NumberFormatException ex) {
            return KhoaHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa khóa học không thành công.")
                    .errors(Arrays.asList(
                            ErrorResponse.builder()
                                    .error_fields(Arrays.asList("id"))
                                    .message("Id nhập không đúng format!")
                                    .build()
                    ))
                    .build();

        } catch (KhoaHocIsNotExist ex) {
            return KhoaHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa khóa học không thành công.")
                    .errors(Arrays.asList(
                            ErrorResponse.builder()
                                    .error_fields(Arrays.asList("id"))
                                    .message("Khóa học không tồn tại")
                                    .build()
                    ))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return KhoaHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa khóa học không thành công.")
                    .errors(Arrays.asList(
                            ErrorResponse.builder()
                                    .message("Lỗi hệ thống!")
                                    .build()
                    ))
                    .build();
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

            return PhongHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xóa phòng học thành công.")
                    .build();
        } catch (PhongHocIsNotExist ex) {
            return PhongHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa phòng học không thành công.")
                    .errors(Arrays.asList(
                            ErrorResponse.builder()
                                    .error_fields(Arrays.asList("id"))
                                    .message("Phòng học không tồn tại!")
                                    .build()
                    ))
                    .build();
        } catch (NumberFormatException ex) {
            return PhongHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa phòng học không thành công.")
                    .errors(Arrays.asList(
                            ErrorResponse.builder()
                                    .error_fields(Arrays.asList("id"))
                                    .message("Phòng hoc ID không đúng format!")
                                    .build()
                    ))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public PhongHocResponse themPhongHoc(ThemPhongHocInputs inputs) {

        try {
            PhongHoc _phongHoc = phongHocService.themPhongHoc(inputs);

            return PhongHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Thêm phòng học thành công.")
                    .data(Arrays.asList(_phongHoc))
                    .build();
        } catch (DayNhaIsNotExistException ex) {
            return PhongHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm phòng học không thành công.")
                    .errors(Arrays.asList(
                            ErrorResponse.builder()
                                    .error_fields(Arrays.asList("dayNhaId"))
                                    .message("Dãy nhà không tồn tại!")
                                    .build()
                    ))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return PhongHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm phòng học không thành công.")
                    .errors(Arrays.asList(
                            ErrorResponse.builder()
                                    .message("Lỗi hệ thống!")
                                    .build()
                    ))
                    .build();
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

            return DayNhaResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xóa dãy nhà thành công.")
                    .data(_dayNhaDeleted)
                    .build();
        } catch (DayNhaIsNotExistException ex) {
            return DayNhaResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa dãy nhà không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder().message("Dãy nhà không tồn tại!").build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DayNhaResponse suaDayNha(SuaDayNhaInput inputs) {


        DayNha _dayNhaInput = DayNha.builder()
                .id(inputs.getId())
                .tenDayNha(inputs.getTenDayNha())
                .moTa(inputs.getMoTa())
                .build();

        try {
            DayNha _dayNhaResponse = dayNhaService.updateDayNha(_dayNhaInput);

            return DayNhaResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Sửa dãy nhà thành công!")
                    .data(Arrays.asList(_dayNhaResponse))
                    .build();
        } catch (DayNhaIsNotExistException ex) {
            return DayNhaResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sửa dãy nhà không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Dãy nhà không tồn tại trong hệ thống!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return DayNhaResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sửa dãy nhà không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DayNhaResponse themDayNha(ThemDayNhaInput inputs) {
        DayNha _dayNhaInput = DayNha.builder()
                .tenDayNha(inputs.getTenDayNha())
                .moTa(inputs.getMoTa())
                .build();

        DayNha _dayNhaResponse = dayNhaService.themDayNha(_dayNhaInput);

        return DayNhaResponse.builder()
                .status(ResponseStatus.OK)
                .message("Thêm dãy nhà thành công!")
                .data(Arrays.asList(_dayNhaResponse))
                .build();
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

            LoginData _loginData = LoginData.builder()
                    .token(_token)
                    .sinhVien(_sinhVien)
                    .build();

            return LoginResponse.builder()
                    .data(_loginData)
                    .status(ResponseStatus.OK)
                    .message("Đăng nhập thành công.")
                    .build();
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            return LoginResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Đăng nhập không thành công!")
                    .errors(new ArrayList<>() {
                        {
                            add(ErrorResponse.builder().message("Tên tài khoản hoặc mật khẩu không đúng!").build());
                        }
                    })
                    .build();
        }

    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public RegisterResponse register(CreateAccountInput inputs) {
        try {
            Account _account = accountService.createAccount(null, inputs);

            return RegisterResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Tạo tài khoản thành công!")
                    .data(_account)
                    .build();
        } catch (UserAlreadyExistsException UAEexp) {
            return RegisterResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Tạo tài khoản không thành công!")
                    .errors(new ArrayList<>() {{
                        add(ErrorResponse.builder()
                                .message("Tên tài khoản đã tồn tại!")
                                .error_fields(new ArrayList<>(Arrays.asList("username")))
                                .build());
                    }})
                    .build();
        } catch (Exception exp) {
            return RegisterResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Tạo tài khoản không thành công!")
                    .errors(new ArrayList<>() {{
                        add(ErrorResponse.builder()
                                .message("Tên tài khoản đã tồn tại!")
                                .error_fields(new ArrayList<>(Arrays.asList("username")))
                                .build());
                    }})
                    .build();
        }
    }
}
