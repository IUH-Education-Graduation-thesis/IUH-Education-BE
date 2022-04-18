package com.hong_hoan.iuheducation.resolvers;

import com.hong_hoan.iuheducation.entity.*;
import com.hong_hoan.iuheducation.exception.KhoaHocIsNotExist;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import com.hong_hoan.iuheducation.resolvers.input.chuyen_nganh.FindChuyenNganhInputs;
import com.hong_hoan.iuheducation.resolvers.input.day_nha.FindDayNhaInputs;
import com.hong_hoan.iuheducation.resolvers.input.giang_vien.FindGiangVienInputs;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.FindHocPhanInputs;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.KieuDangKy;
import com.hong_hoan.iuheducation.resolvers.input.khoa_hoc.FindKhoaHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.khoa_vien.FindKhoaVienInputs;
import com.hong_hoan.iuheducation.resolvers.input.lop.FindLopHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.phong_hoc.FindPhongHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.sinh_vien.FindSinhVienInputs;
import com.hong_hoan.iuheducation.resolvers.response.chuyen_nganh.FindChuyenNganhResponse;
import com.hong_hoan.iuheducation.resolvers.response.giang_vien.FindGiangVienResponse;
import com.hong_hoan.iuheducation.resolvers.response.giang_vien.PaginationGiangVien;
import com.hong_hoan.iuheducation.resolvers.response.hoc_ky_normal.HocKyNormalResponse;
import com.hong_hoan.iuheducation.resolvers.response.hoc_phan.FindHocPhanResponse;
import com.hong_hoan.iuheducation.resolvers.response.hoc_phan.HocPhanResponse;
import com.hong_hoan.iuheducation.resolvers.response.hoc_phan.PaginationHocPhan;
import com.hong_hoan.iuheducation.resolvers.response.khoa_hoc.FindKhoaHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.khoa_hoc.KhoaHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.khoa_hoc.PaginationKhoaHoc;
import com.hong_hoan.iuheducation.resolvers.response.khoa_vien.FindKhoaVienResponse;
import com.hong_hoan.iuheducation.resolvers.response.khoa_vien.PaginationKhoaVien;
import com.hong_hoan.iuheducation.resolvers.response.lich_hoc.GetLichHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.lich_hoc.LichHocFormat;
import com.hong_hoan.iuheducation.resolvers.response.lop.FindLopHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.lop.PaginationLopHoc;
import com.hong_hoan.iuheducation.resolvers.response.lop_hoc_phan.GetLopHocPhanResponse;
import com.hong_hoan.iuheducation.resolvers.response.ProfileResponse;
import com.hong_hoan.iuheducation.resolvers.response.day_nha.DayNhaResponse;
import com.hong_hoan.iuheducation.resolvers.response.phong_hoc.PhongHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.sinh_vien.FindSinhVienResponse;
import com.hong_hoan.iuheducation.resolvers.response.sinh_vien.PaginationSinhVien;
import com.hong_hoan.iuheducation.service.*;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
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
    private KhoaVienService khoaVienService;
    @Autowired
    private GiangVienService giangVienService;
    @Autowired
    private LopService lopService;
    @Autowired
    private ChuyenNganhService chuyenNganhService;
    @Autowired
    private HocPhanService hocPhanService;
    @Autowired
    private LopHocPhanService lopHocPhanService;
    @Autowired
    private SinhVienService sinhVienService;
    @Autowired
    private LichHocService lichHocService;
    @Autowired
    private HocKyNormalService hocKyNormalService;

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public HocKyNormalResponse getListHocKy() {
        try {
            Account _account = accountService.getCurrentAccount();
            List<HocKyNormal> _listHocKyNormal = hocKyNormalService.getListHocKyNormalOfSinhVien(_account);

            return HocKyNormalResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Lấy thông tin học kỳ thành công.")
                    .data(_listHocKyNormal)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return HocKyNormalResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Lấy thông tin học kỳ không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    public GetLopHocPhanResponse getLopHocPhanDaDangKy(Long hocKyId) {

        try {
            Account _account = accountService.getCurrentAccount();
            List<LopHocPhan> _lopHocPhans = lopHocPhanService.getLopHocPhanDaDangKy(hocKyId, _account);


            return GetLopHocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Lấy danh sách lớp học phần thành công.")
                    .data(_lopHocPhans)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return GetLopHocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Lấy danh sách lớp học phần không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    public GetLichHocResponse getLichHoc(Date ngay) {
        Account _account = accountService.getCurrentAccount();

        try {
            List<LichHocFormat> _lichHocFormats = lichHocService.getLichHoc(ngay, _account);

            return GetLichHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Lấy thông tin lịch học thành công.")
                    .data(_lichHocFormats)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return GetLichHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Lấy thông tin lịch học không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public HocPhanResponse getListHocPhanDKHP(Integer hocKyDangKy, KieuDangKy kieuDangKy) {
        try {
            Account _account = accountService.getCurrentAccount();

            List<HocPhan> _listHocPhan = hocPhanService.getListHocPhanForDKHP(hocKyDangKy, kieuDangKy, _account);

            return HocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Lấy thông tin học phần thành công!")
                    .data(_listHocPhan)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return HocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Lấy thông tin học phần không thành công!")
                    .build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    public FindSinhVienResponse findSinhVien(FindSinhVienInputs inputs) {
        try {
            PaginationSinhVien _paginationSinhVien = sinhVienService.findSinhVienWithFilter(inputs);

            return FindSinhVienResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Tìm kiếm sinh viên thành công.")
                    .data(Arrays.asList(_paginationSinhVien))
                    .build();

        } catch (Exception ex) {
            return FindSinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Tìm kiếm sinh viên không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    public GetLopHocPhanResponse getLopHocPhan(String id) {
        try {
            List<LopHocPhan> _listLopHocPhan = lopHocPhanService.getLopHocPhanWithId(id);

            return GetLopHocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Tìm kiếm lớp học phần thành công.")
                    .data(_listLopHocPhan)
                    .build();
        } catch (Exception ex) {
            return GetLopHocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Tìm kiếm lớp học phần không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    public FindHocPhanResponse findHocPhans(FindHocPhanInputs inputs) {
        try {
            PaginationHocPhan _paginationHocPhan = hocPhanService.findHocPhanWithPagination(inputs);

            return FindHocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Tìm kiếm học phần thành công.")
                    .data(Arrays.asList(_paginationHocPhan))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return FindHocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Tìm kiếm học phần thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    public FindChuyenNganhResponse findChuyenNganh(FindChuyenNganhInputs inputs) {
        try {
            List<ChuyenNganh> _listChuyenNganh = chuyenNganhService.findListChuyeNganh(inputs);
            return FindChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Tìm kiếm chuyên ngành thành công.")
                    .data(_listChuyenNganh)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return FindChuyenNganhResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Tìm kiếm chuyên ngành không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    public FindLopHocResponse findLopHoc(FindLopHocInputs inputs) {
        try {
            PaginationLopHoc _paginationLopHoc = lopService.findLopHocPagination(inputs);
            return FindLopHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Lấy thông tin lớp học thành công!")
                    .data(Arrays.asList(_paginationLopHoc))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return FindLopHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Lấy thông tin lớp học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    public FindGiangVienResponse findGiangVien(FindGiangVienInputs inputs) {
        try {
            PaginationGiangVien _paginationGiangVien = giangVienService.findGiangVienPagination(inputs);

            return FindGiangVienResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Tìm kiếm giảng viên thành công.")
                    .data(Arrays.asList(_paginationGiangVien))
                    .build();

        } catch (Exception ex) {
            ex.printStackTrace();

            return FindGiangVienResponse.builder()
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
