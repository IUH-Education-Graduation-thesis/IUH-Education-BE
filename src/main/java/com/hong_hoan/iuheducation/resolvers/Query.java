package com.hong_hoan.iuheducation.resolvers;

import com.hong_hoan.iuheducation.entity.*;
import com.hong_hoan.iuheducation.exception.KhoaHocIsNotExist;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import com.hong_hoan.iuheducation.resolvers.input.day_nha.FindDayNhaInputs;
import com.hong_hoan.iuheducation.resolvers.input.khoa_hoc.FindKhoaHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.khoa_vien.FindKhoaVienInputs;
import com.hong_hoan.iuheducation.resolvers.input.nam_hoc.FindNamHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.phong_hoc.FindPhongHocInputs;
import com.hong_hoan.iuheducation.resolvers.response.khoa_hoc.FindKhoaHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.khoa_hoc.KhoaHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.khoa_hoc.PaginationKhoaHoc;
import com.hong_hoan.iuheducation.resolvers.response.khoa_vien.FindKhoaVienResponse;
import com.hong_hoan.iuheducation.resolvers.response.khoa_vien.PaginationKhoaVien;
import com.hong_hoan.iuheducation.resolvers.response.nam_hoc.FindNamHocRest;
import com.hong_hoan.iuheducation.resolvers.response.nam_hoc.FindNamHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.ProfileResponse;
import com.hong_hoan.iuheducation.resolvers.response.day_nha.DayNhaResponse;
import com.hong_hoan.iuheducation.resolvers.response.phong_hoc.PhongHocResponse;
import com.hong_hoan.iuheducation.service.*;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {
    @Autowired
    private DayNhaService dayNhaService;
    @Autowired
    private PhongHocService phongHocService;
    @Autowired
    private KhoaHocService khoaHocService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private NamHocService namHocService;
    @Autowired
    private KhoaVienService khoaVienService;

    @PreAuthorize("isAuthenticated()")
    public FindKhoaVienResponse findKhoaVien(FindKhoaVienInputs inputs) {
        try {
            PaginationKhoaVien _paginationKhoaVien = khoaVienService.getListKhoaVienPagination(inputs);
            return FindKhoaVienResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Tìm kiếm khoa viện thành công.")
                    .data(Arrays.asList(_paginationKhoaVien))
                    .build();
        } catch (Exception ex) {
            return FindKhoaVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Tìm kiếm khoa viện không thành công!")
                    .errors(Arrays.asList(
                            ErrorResponse.builder()
                                    .message("Lỗi hệ thống!")
                                    .build()
                    ))
                    .build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    public FindNamHocResponse findNamHoc(FindNamHocInputs inputs) {
        try {
            FindNamHocRest _findNamHocRest = namHocService.findNamHocs(inputs);

            return FindNamHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Lấy danh sách lớp học thành công!")
                    .data(Arrays.asList(_findNamHocRest))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return FindNamHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Lấy danh sách lớp học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    public ProfileResponse getProfile() {
        try {
            Account _account = accountService.getCurrentAccount();

            return ProfileResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Lấy thông tin tài khoản thành công!")
                    .data(Arrays.asList(_account))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ProfileResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Lấy thông tin tài khoản không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT', 'TEACHER')")
    public FindKhoaHocResponse findKhoaHocs(FindKhoaHocInputs inputs) {
        PaginationKhoaHoc _paginationKhoaHoc = khoaHocService.findKhoaHocs(inputs);

        return FindKhoaHocResponse.builder()
                .status(ResponseStatus.OK)
                .message("Lấy thông tin khóa học thành công!")
                .data(Arrays.asList(_paginationKhoaHoc))
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public PhongHocResponse findPhongHocs(FindPhongHocInputs inputs) {
        System.out.println(inputs);

        if (inputs == null) {
            List<PhongHoc> _listPhongHoc = phongHocService.findAllPhongHoc();

            return PhongHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Lấy danh sách lớp học thành công!")
                    .data(_listPhongHoc)
                    .build();
        }


        try {
            String _dayNhaId = inputs.getDayNhaId();
            if (!_dayNhaId.isEmpty()) {

                long _dayNhaIdLong = Long.valueOf(_dayNhaId);
                List<PhongHoc> _phongHoc = phongHocService.findPhongHocByDayNha(_dayNhaIdLong);

                return PhongHocResponse.builder()
                        .status(ResponseStatus.OK)
                        .message("Lấy danh sách lớp học thành công!")
                        .data(_phongHoc)
                        .build();

            }

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return PhongHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Lấy danh sách lớp học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .error_fields(Arrays.asList("dayNhaId"))
                            .message("Day nha id không phải số nguyên!")
                            .build()))
                    .build();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            return PhongHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Lấy danh sách lớp học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }

        try {
            String _id = inputs.getId();
            long _idLong = Long.valueOf(_id);
            PhongHoc _phongHoc = phongHocService.findPhongHocById(_idLong);

            if (_phongHoc == null) {
                return PhongHocResponse.builder()
                        .status(ResponseStatus.OK)
                        .message("Lấy danh sách lớp học thành công!")
                        .data(Arrays.asList())
                        .build();
            }

            return PhongHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Lấy danh sách lớp học thành công!")
                    .data(Arrays.asList(_phongHoc))
                    .build();

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return PhongHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Lấy danh sách lớp học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .error_fields(Arrays.asList("dayNhaId"))
                            .message("Day nha id không phải số nguyên!")
                            .build()))
                    .build();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            List<PhongHoc> _listPhongHoc = phongHocService.findAllPhongHoc();

            return PhongHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Lấy danh sách lớp học thành công!")
                    .data(_listPhongHoc)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return PhongHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Lấy danh sách lớp học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }

    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DayNhaResponse findDayNha(FindDayNhaInputs inputs) {
        List<DayNha> _listDayNha = dayNhaService.getListDayNha(inputs);

        return DayNhaResponse.builder()
                .status(ResponseStatus.OK)
                .message("lấy thông tin lớp học thành công.")
                .data(_listDayNha)
                .build();
    }

}
