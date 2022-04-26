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
import com.hong_hoan.iuheducation.resolvers.input.hoc_ky.ThemHocKyInputs;
import com.hong_hoan.iuheducation.resolvers.input.hoc_phan.DangKyHocPhanInputs;
import com.hong_hoan.iuheducation.resolvers.input.khoa_hoc.ThemKhoaHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.khoa_vien.ThemKhoaVienInputs;
import com.hong_hoan.iuheducation.resolvers.input.lich_hoc.ThemLichHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.lop.ThemLopInputs;
import com.hong_hoan.iuheducation.resolvers.input.mon_hoc.ThemMonHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.phong_hoc.ThemPhongHocInputs;
import com.hong_hoan.iuheducation.resolvers.input.sinh_vien.SinhVienInputs;
import com.hong_hoan.iuheducation.resolvers.response.DangKyHocPhanResponse;
import com.hong_hoan.iuheducation.resolvers.response.HocKyResponse;
import com.hong_hoan.iuheducation.resolvers.response.chuyen_nganh.ChuyenNganhResponse;
import com.hong_hoan.iuheducation.resolvers.response.giang_vien.GiangVienResponse;
import com.hong_hoan.iuheducation.resolvers.response.khoa_hoc.KhoaHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.khoa_vien.KhoaVienResponse;
import com.hong_hoan.iuheducation.resolvers.response.lop.LopResponse;
import com.hong_hoan.iuheducation.resolvers.response.mon_hoc.MonHocRespone;
import com.hong_hoan.iuheducation.resolvers.response.account.LoginData;
import com.hong_hoan.iuheducation.resolvers.response.account.LoginResponse;
import com.hong_hoan.iuheducation.resolvers.response.account.RegisterResponse;
import com.hong_hoan.iuheducation.resolvers.response.day_nha.DayNhaResponse;
import com.hong_hoan.iuheducation.resolvers.response.lich_hoc.LichHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.phong_hoc.PhongHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.sinh_vien.SinhVienResponse;
import com.hong_hoan.iuheducation.resolvers.response.sinh_vien.SuccessAndFailSinhVien;
import com.hong_hoan.iuheducation.resolvers.response.sinh_vien.ThemSinhVienWithFileResponse;
import com.hong_hoan.iuheducation.service.*;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.math3.analysis.function.Sinh;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import reactor.core.scheduler.Schedulers;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
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


    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public DangKyHocPhanResponse dangKyHocPhan(List<DangKyHocPhanInputs> inputs) {
        Account _account = accountService.getCurrentAccount();

        try {
            List<SinhVienLopHocPhan> _sinhVienLopHocPhans = sinhVienLopHocPhanService.themSinhVienLopHocPhan(inputs, _account);

            List<HocPhan> _hocPhans = _sinhVienLopHocPhans.stream().map(i -> i.getLopHocPhan().getHocPhan()).collect(Collectors.toList());

            return DangKyHocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Đăng ký học phần thành công.")
                    .data(_hocPhans)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return DangKyHocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Đăng ký học phần Không thành công.")
                    .build();
        }

    }

    /*
     * Sinh vien
     *
     * */

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SinhVienResponse suaSinhVien(SinhVienInputs inputs, Long sinhVienId) {
        try {
            SinhVien _sinhVien = sinhVienService.suaSinhVien(inputs, sinhVienId);

            return SinhVienResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(Arrays.asList(_sinhVien))
                    .message("Sửa sinh viên thành công.")
                    .build();
        } catch (SinhVienIsNotExist ex) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sửa sinh viên không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Không tìm thấy sinh viên!")
                            .build()))
                    .build();

        } catch (LopIsNotExist e) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sửa sinh viên không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Không tìm thấy lớp học!")
                            .build()))
                    .build();

        } catch (Exception ex) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sửa sinh viên không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
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
                    .message("Xóa sinh viên thành công!")
                    .data(_listSinhVien)
                    .build();
        } catch (SinhVienIsNotExist ex) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa sinh viên không thành công!")
                    .errors(Arrays.asList(
                            ErrorResponse.builder()
                                    .message("Sinh viên không tồn tại!")
                                    .build()
                    ))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa sinh viên không thành công!")
                    .errors(Arrays.asList(
                            ErrorResponse.builder()
                                    .message("Lỗi hệ thống!")
                                    .build()
                    ))
                    .build();
        }


    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ThemSinhVienWithFileResponse themSinhViens(List<Part> files, DataFetchingEnvironment env) throws IOException {
        DefaultGraphQLServletContext _context = env.getContext();

        try {
            Part _part = _context.getFileParts().get(0);
            SuccessAndFailSinhVien _successAndFailSinhVien = sinhVienService.addSinhVienWithFile(_part);
            return ThemSinhVienWithFileResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Thêm sinh viên thành công.")
                    .data(Arrays.asList(_successAndFailSinhVien))
                    .build();
        } catch (IndexOutOfBoundsException ex) {
            return ThemSinhVienWithFileResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Thêm sinh viên không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Không tìm thấy file được thêm!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ThemSinhVienWithFileResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Thêm sinh viên không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        } catch (Throwable e) {
            e.printStackTrace();
            return ThemSinhVienWithFileResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Thêm sinh viên không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SinhVienResponse themSinhVien(SinhVienInputs inputs) {
        try {
            SinhVien _sinhVien = sinhVienService.addSinhVien(inputs);

            return SinhVienResponse.builder().status(ResponseStatus.OK).message("Thêm sinh viên thành công.").data(Arrays.asList(_sinhVien)).build();

        } catch (NumberFormatException ex) {
            return SinhVienResponse.builder().status(ResponseStatus.OK).message("Thêm sinh viên không thành công.").errors(Arrays.asList(ErrorResponse.builder().message("Lớp học không tồn tại!").error_fields(Arrays.asList("lopId")).build())).build();
        } catch (LopIsNotExist ex) {
            return SinhVienResponse.builder().status(ResponseStatus.OK).message("Thêm sinh viên không thành công.").errors(Arrays.asList(ErrorResponse.builder().message("Lớp học không tồn tại!").error_fields(Arrays.asList("lopId")).build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return SinhVienResponse.builder().status(ResponseStatus.OK).message("Thêm sinh viên không thành công.").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
        }
    }


     /*
        lop
        ======================================================================
     */

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public LopResponse themLop(ThemLopInputs inputs) {
        try {
            Lop _lop = lopService.themLop(inputs);

            return LopResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Thêm lớp thành công.")
                    .data(Arrays.asList(_lop))
                    .build();
        } catch (KhoaHocIsNotExist ex) {
            return LopResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm lớp không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Khóa học không tồn tại!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            return LopResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm lớp không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
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
                    .message("Thêm lớp thành công.")
                    .data(Arrays.asList(_lop))
                    .build();
        } catch (LopIsNotExist ex) {
            return LopResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm lớp không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lớp học không tồn tại!")
                            .build()))
                    .build();
        } catch (KhoaHocIsNotExist ex) {
            return LopResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm lớp không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Khóa học không tồn tại!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            return LopResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm lớp không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
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
                    .message("Sủa giảng viên thành công.")
                    .data(Arrays.asList(_giangVien))
                    .build();
        } catch (GiangVienIsNotExistException ex) {

            return GiangVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sủa giảng viên không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Giảng viên không tồn tại!")
                            .build()))
                    .build();

        } catch (ChuyenNganhIsNotExistExcepton ex) {
            return GiangVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sủa giảng viên không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Chuyên ngành không tồn tại!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            return GiangVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sủa giảng viên không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public GiangVienResponse themGiangVien(ThemGiangVienInputs inputs) {
        try {
            GiangVien _giangVienInput = giangVienService.themGiangVien(inputs);
            return GiangVienResponse.builder().status(ResponseStatus.OK).message("Thêm giảng viên thành công!").data(List.of(_giangVienInput)).build();
        } catch (ChuyenNganhIsNotExistExcepton ex) {
            return GiangVienResponse.builder().status(ResponseStatus.ERROR).message("Thêm giảng viên không thành công!").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("chuyenNganhID")).message("Chuyên ngành không tồn tại!").build())).build();
        } catch (Exception ex) {
            return GiangVienResponse.builder().status(ResponseStatus.ERROR).message("Thêm giảng viên không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public GiangVienResponse xoaGiangViens(Set<Long> ids) {
        try {
            List<GiangVien> _idGiangVien = giangVienService.xoaGiangViens(ids);
            return GiangVienResponse.builder().status(ResponseStatus.OK).message("Xóa giảng viên thành công").data(_idGiangVien).build();
        } catch (Exception exception) {
            return GiangVienResponse.builder().status(ResponseStatus.ERROR).message("Xóa giảng viên không thành công").errors(Arrays.asList(ErrorResponse.builder().message("Giảng viên không tồn tại!").build())).build();
        }
    }

    /*
        mon hoc
        ======================================================================
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public MonHocRespone suaMonHoc(ThemMonHocInputs inputs, Long id) {
        try {
            MonHoc _monHoc = monHocService.suaMonHoc(inputs, id);

            return MonHocRespone.builder()
                    .status(ResponseStatus.OK)
                    .message("Sửa môn học hành công.")
                    .data(Arrays.asList(_monHoc))
                    .build();
        } catch (MonHocIsExistException ex) {
            return MonHocRespone.builder()
                    .status(ResponseStatus.OK)
                    .message("Sửa môn học không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Môn học không tồn tại!")
                            .build()))
                    .build();
        } catch (KhoaVienIsNotExistException ex) {
            return MonHocRespone.builder()
                    .status(ResponseStatus.OK)
                    .message("Sửa môn học không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Khoa viện không tồn tại!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return MonHocRespone.builder()
                    .status(ResponseStatus.OK)
                    .message("Sửa môn học không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public MonHocRespone themMonHoc(ThemMonHocInputs inputs) {
        try {
            MonHoc _monHocInput = monHocService.themMonHoc(inputs);
            return MonHocRespone.builder().status(ResponseStatus.OK).message("Thêm môn học thành công!").data(List.of(_monHocInput)).build();
        } catch (KhoaVienIsNotExistException ex) {
            return MonHocRespone.builder().status(ResponseStatus.ERROR).message("Thêm môn học không thành công!").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("khoaVienID")).message("Khoa viện không tồn tại!").build())).build();
        } catch (Exception ex) {
            return MonHocRespone.builder().status(ResponseStatus.ERROR).message("Thêm môn học không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public MonHocRespone xoaMonHocs(Set<Long> ids) {
        try {
            List<MonHoc> _idMonHoc = monHocService.xoaMonHocs(ids);
            return MonHocRespone.builder().status(ResponseStatus.OK).message("Xóa môn học thành công").data(_idMonHoc).build();
        } catch (Exception exception) {
            return MonHocRespone.builder().status(ResponseStatus.ERROR).message("Xóa môn học không thành công").errors(Arrays.asList(ErrorResponse.builder().message("Môn học không tồn tại!").build())).build();
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
                    .message("Sửa chuyên ngành thành công.")
                    .data(Arrays.asList(_chuyenNganh))
                    .build();
        } catch (KhoaVienIsNotExistException ex) {
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Sửa chuyên ngành không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Khoa viện không tồn tại.")
                            .build()))
                    .build();
        } catch (ChuyenNganhIsNotExistExcepton ex) {
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Sửa chuyên ngành không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Chuyên ngành không tồn tại.")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Sửa chuyên ngành không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống.")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ChuyenNganhResponse themChuyenNganh(ThemChuyenNganhInputs inputs) {
        System.out.println(inputs);
        try {
            ChuyenNganh _chuyenNganhInput = chuyenNganhService.themChuyenNganh(inputs);
            return ChuyenNganhResponse.builder().status(ResponseStatus.OK).message("Thêm chuyên ngành thành công!").data(List.of(_chuyenNganhInput)).build();
        } catch (KhoaVienIsNotExistException ex) {
            return ChuyenNganhResponse.builder().status(ResponseStatus.ERROR).message("Thêm chuyên ngành không thành công!").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("khoaVienID")).message("Khoa viện không tồn tại!").build())).build();
        } catch (Exception ex) {
            return ChuyenNganhResponse.builder().status(ResponseStatus.ERROR).message("Thêm chuyên ngành không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ChuyenNganhResponse xoaChuyenNganhs(Set<Long> ids) {
        try {
            List<ChuyenNganh> _idChuyenNganh = chuyenNganhService.xoaChuyenNganhs(ids);
            return ChuyenNganhResponse.builder().status(ResponseStatus.OK).message("Xóa chuyên ngành thành công").data(_idChuyenNganh).build();
        } catch (Exception exception) {
            return ChuyenNganhResponse.builder().status(ResponseStatus.ERROR).message("Xóa chuyên ngành không thành công").errors(Arrays.asList(ErrorResponse.builder().message("Chuyên ngành không tồn tại!").build())).build();
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
                    .message("Sửa khoa viện không thành công!")
                    .status(ResponseStatus.ERROR)
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public KhoaVienResponse themKhoaVien(ThemKhoaVienInputs inputs) {
        try {
            KhoaVien _khoaVienInput = khoaVienService.themKhoaVien(inputs);
            return KhoaVienResponse.builder().status(ResponseStatus.OK).message("Thêm khoa viện thành công").data(List.of(_khoaVienInput)).build();
        } catch (Exception ex) {
            return KhoaVienResponse.builder().status(ResponseStatus.ERROR).message("Thêm khoa viện không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public KhoaVienResponse xoaKhoaViens(Set<Long> ids) {
        try {
            List<KhoaVien> _idKhoaVien = khoaVienService.xoaKhoaViens(ids);
            return KhoaVienResponse.builder().status(ResponseStatus.OK).message("Xóa khoa viện thành công").data(_idKhoaVien).build();
        } catch (Exception exception) {
            return KhoaVienResponse.builder().status(ResponseStatus.ERROR).message("Xóa khoa viện không thành công").errors(Arrays.asList(ErrorResponse.builder().message("Khoa viện không tồn tại!").build())).build();
        }
    }

    /*
        lich hoc
        ======================================================================
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public LichHocResponse themLichHoc(ThemLichHocInputs inputs) {
        try {
            LichHoc _lichHoc = lichHocService.themLichHoc(inputs);

            return LichHocResponse.builder().status(ResponseStatus.OK).message("Thêm lịch học thành công").data(Arrays.asList(_lichHoc)).build();
        } catch (PhongHocIsNotExist ex) {
            return LichHocResponse.builder().status(ResponseStatus.ERROR).message("Thêm lịch học không thành công").errors(Arrays.asList(ErrorResponse.builder().message("Phòng học không tồn tại").build())).build();
        } catch (Exception ex) {
            return LichHocResponse.builder().status(ResponseStatus.ERROR).message("Thêm lịch học không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
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
            return HocKyResponse.builder().status(ResponseStatus.OK).message("Xóa học kỳ thành công.").build();
        } catch (NumberFormatException ex) {
            return HocKyResponse.builder().status(ResponseStatus.ERROR).message("Xóa học kỳ không thành công!").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("id")).message("Hoc ky không tồn tại!").build())).build();
        } catch (HocKyIsNotExist ex) {
            return HocKyResponse.builder().status(ResponseStatus.ERROR).message("Xóa học kỳ không thành công!").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("id")).message("Hoc ky không tồn tại!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return HocKyResponse.builder().status(ResponseStatus.ERROR).message("Xóa học kỳ không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public HocKyResponse themHocKy(ThemHocKyInputs inputs) {
        try {
            HocKy _hocKy = hocKyService.themHocKy(inputs);

            return HocKyResponse.builder().status(ResponseStatus.OK).message("Thêm học kỳ thành công!").data(Arrays.asList(_hocKy)).build();
        } catch (NumberFormatException ex) {
            return HocKyResponse.builder().status(ResponseStatus.ERROR).message("Thêm học kỳ không thành công!").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("namHocId")).message("Năm học không tồn tại!").build())).build();
        } catch (NamHocIsNotExist ex) {
            return HocKyResponse.builder().status(ResponseStatus.ERROR).message("Thêm học kỳ không thành công!").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("namHocId")).message("Năm học không tồn tại!").build())).build();
        } catch (Exception ex) {
            return HocKyResponse.builder().status(ResponseStatus.ERROR).message("Thêm học kỳ không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
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
                    .message("Sửa khóa học thành công.")
                    .data(Arrays.asList(_khoa))
                    .build();
        } catch (KhoaHocIsNotExist ex) {
            return KhoaHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sửa khóa học không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Khóa học không tồn tại!")
                            .build()))
                    .build();
        } catch (ChuyenNganhIsNotExistExcepton ex) {
            return KhoaHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sửa khóa học không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("chuyên ngành không tồn tại!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            return KhoaHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sửa khóa học không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public KhoaHocResponse themKhoaHoc(ThemKhoaHocInputs inputs) {

        try {
            Khoa _khoa = khoaHocService.createKhoaHoc(inputs);

            return KhoaHocResponse.builder().status(ResponseStatus.OK).message("Thêm khóa học thành công.").data(Arrays.asList(_khoa)).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return KhoaHocResponse.builder().status(ResponseStatus.ERROR).message("Thêm khóa học không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
        }


    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public KhoaHocResponse xoaKhoaHocs(List<Long> ids) {
        try {
            List<Khoa> _listKhoa = khoaHocService.deleteKhoaHoc(ids);

            return KhoaHocResponse.builder().status(ResponseStatus.OK)
                    .data(_listKhoa)
                    .message("Xóa khóa học thành công.").build();

        } catch (NumberFormatException ex) {
            return KhoaHocResponse.builder().status(ResponseStatus.ERROR).message("Xóa khóa học không thành công.").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("id")).message("Id nhập không đúng format!").build())).build();

        } catch (KhoaHocIsNotExist ex) {
            return KhoaHocResponse.builder().status(ResponseStatus.ERROR).message("Xóa khóa học không thành công.").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("id")).message("Khóa học không tồn tại").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return KhoaHocResponse.builder().status(ResponseStatus.ERROR).message("Xóa khóa học không thành công.").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
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

            return PhongHocResponse.builder().status(ResponseStatus.OK).message("Xóa phòng học thành công.").build();
        } catch (PhongHocIsNotExist ex) {
            return PhongHocResponse.builder().status(ResponseStatus.ERROR).message("Xóa phòng học không thành công.").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("id")).message("Phòng học không tồn tại!").build())).build();
        } catch (NumberFormatException ex) {
            return PhongHocResponse.builder().status(ResponseStatus.ERROR).message("Xóa phòng học không thành công.").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("id")).message("Phòng hoc ID không đúng format!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public PhongHocResponse themPhongHoc(ThemPhongHocInputs inputs) {

        try {
            PhongHoc _phongHoc = phongHocService.themPhongHoc(inputs);

            return PhongHocResponse.builder().status(ResponseStatus.OK).message("Thêm phòng học thành công.").data(Arrays.asList(_phongHoc)).build();
        } catch (DayNhaIsNotExistException ex) {
            return PhongHocResponse.builder().status(ResponseStatus.ERROR).message("Thêm phòng học không thành công.").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("dayNhaId")).message("Dãy nhà không tồn tại!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return PhongHocResponse.builder().status(ResponseStatus.ERROR).message("Thêm phòng học không thành công.").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
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

            return DayNhaResponse.builder().status(ResponseStatus.OK).message("Xóa dãy nhà thành công.").data(_dayNhaDeleted).build();
        } catch (DayNhaIsNotExistException ex) {
            return DayNhaResponse.builder().status(ResponseStatus.ERROR).message("Xóa dãy nhà không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Dãy nhà không tồn tại!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DayNhaResponse suaDayNha(SuaDayNhaInput inputs) {


        DayNha _dayNhaInput = DayNha.builder().id(inputs.getId()).tenDayNha(inputs.getTenDayNha()).moTa(inputs.getMoTa()).build();

        try {
            DayNha _dayNhaResponse = dayNhaService.updateDayNha(_dayNhaInput);

            return DayNhaResponse.builder().status(ResponseStatus.OK).message("Sửa dãy nhà thành công!").data(Arrays.asList(_dayNhaResponse)).build();
        } catch (DayNhaIsNotExistException ex) {
            return DayNhaResponse.builder().status(ResponseStatus.ERROR).message("Sửa dãy nhà không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Dãy nhà không tồn tại trong hệ thống!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return DayNhaResponse.builder().status(ResponseStatus.ERROR).message("Sửa dãy nhà không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DayNhaResponse themDayNha(ThemDayNhaInput inputs) {
        DayNha _dayNhaInput = DayNha.builder().tenDayNha(inputs.getTenDayNha()).moTa(inputs.getMoTa()).build();

        DayNha _dayNhaResponse = dayNhaService.themDayNha(_dayNhaInput);

        return DayNhaResponse.builder().status(ResponseStatus.OK).message("Thêm dãy nhà thành công!").data(Arrays.asList(_dayNhaResponse)).build();
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

            return LoginResponse.builder().data(_loginData).status(ResponseStatus.OK).message("Đăng nhập thành công.").build();
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            return LoginResponse.builder().status(ResponseStatus.ERROR).message("Đăng nhập không thành công!").errors(new ArrayList<>() {
                {
                    add(ErrorResponse.builder().message("Tên tài khoản hoặc mật khẩu không đúng!").build());
                }
            }).build();
        }

    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public RegisterResponse register(CreateAccountInput inputs) {
        try {
            Account _account = accountService.createAccount(null, inputs);

            return RegisterResponse.builder().status(ResponseStatus.OK).message("Tạo tài khoản thành công!").data(_account).build();
        } catch (UserAlreadyExistsException UAEexp) {
            return RegisterResponse.builder().status(ResponseStatus.ERROR).message("Tạo tài khoản không thành công!").errors(new ArrayList<>() {{
                add(ErrorResponse.builder().message("Tên tài khoản đã tồn tại!").error_fields(new ArrayList<>(Arrays.asList("username"))).build());
            }}).build();
        } catch (Exception exp) {
            return RegisterResponse.builder().status(ResponseStatus.ERROR).message("Tạo tài khoản không thành công!").errors(new ArrayList<>() {{
                add(ErrorResponse.builder().message("Tên tài khoản đã tồn tại!").error_fields(new ArrayList<>(Arrays.asList("username"))).build());
            }}).build();
        }
    }
}
