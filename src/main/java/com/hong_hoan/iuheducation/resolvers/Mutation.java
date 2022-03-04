package com.hong_hoan.iuheducation.resolvers;

import com.hong_hoan.iuheducation.entity.*;
import com.hong_hoan.iuheducation.exception.DayNhaIsNotExistException;
import com.hong_hoan.iuheducation.exception.PhongHocIsNotExist;
import com.hong_hoan.iuheducation.exception.UserAlreadyExistsException;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import com.hong_hoan.iuheducation.resolvers.input.CreateAccountInput;
import com.hong_hoan.iuheducation.resolvers.input.day_nha.SuaDayNhaInput;
import com.hong_hoan.iuheducation.resolvers.input.day_nha.ThemDayNhaInput;
import com.hong_hoan.iuheducation.resolvers.input.khoa_hoc.ThemKhoaHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.phong_hoc.ThemPhongHocInputs;
import com.hong_hoan.iuheducation.resolvers.response.KhoaHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.account.LoginData;
import com.hong_hoan.iuheducation.resolvers.response.account.LoginResponse;
import com.hong_hoan.iuheducation.resolvers.response.account.RegisterResponse;
import com.hong_hoan.iuheducation.resolvers.response.day_nha.DayNhaResponse;
import com.hong_hoan.iuheducation.resolvers.response.phong_hoc.PhongHocResponse;
import com.hong_hoan.iuheducation.service.AccountService;
import com.hong_hoan.iuheducation.service.DayNhaService;
import com.hong_hoan.iuheducation.service.KhoaHocService;
import com.hong_hoan.iuheducation.service.PhongHocService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;

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
    public DayNhaResponse xoaDayNha(long id) {
        try {
            dayNhaService.deleteDayNha(id);

            return DayNhaResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xóa dãy nhà thành công.")
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
