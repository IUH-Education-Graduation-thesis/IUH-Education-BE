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

            return LopHocPhanResponse.builder().status(ResponseStatus.OK).message("Hủy đăng ký lớp học phần thành công!").data(Arrays.asList(_lopHocPhan)).build();
        } catch (LopHocPhanLockedException ex) {
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Hủy đăng ký lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lớp học phần đã khóa, không thể hủy!").build())).build();

        } catch (LopHocPhanIsNotExist ex) {
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Hủy đăng ký lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lớp học phần không tồn tại!").build())).build();
        } catch (SinhVienLopHocPhanIsNotExist ex) {
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Hủy đăng ký lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Sinh viên chưa đăng ký lớp học phần này!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Hủy đăng ký lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public CheckLichTrungResponse checkLichTrung(List<DangKyHocPhanInputs> listLopHocPhanPrepareDangKy, Long hocKyNormalId) {
        try {
            Account _currentAccoutn = accountService.getCurrentAccount();
            CheckLichHocRes _checkLichHocRes = lopHocPhanService.checkLichHoc(listLopHocPhanPrepareDangKy, hocKyNormalId, _currentAccoutn);

            return CheckLichTrungResponse.builder().status(ResponseStatus.OK).message("Kiểm tram lịch trùng thành công.").data(Arrays.asList(_checkLichHocRes)).build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return CheckLichTrungResponse.builder().status(ResponseStatus.OK).message("Kiểm tram lịch trùng thành công.").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SinhVienResponse xoaSinhVienOfLopHocPhan(List<Long> sinhVienIds, Long lopHocPhanId) {
        try {
            List<SinhVien> _listSinhVien = lopHocPhanService.xoaSinhVienOfLopHocPhan(sinhVienIds, lopHocPhanId);

            return SinhVienResponse.builder().status(ResponseStatus.OK).message("Xóa sinh viên khỏi lớp học phần thành công.").data(_listSinhVien).build();
        } catch (LopHocPhanIsNotExist ex) {
            return SinhVienResponse.builder().status(ResponseStatus.ERROR).message("Xóa sinh viên khỏi lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lớp học phần không tồn tại!").build())).build();
        } catch (SinhVienLopHocPhanIsNotExist ex) {
            return SinhVienResponse.builder().status(ResponseStatus.ERROR).message("Xóa sinh viên khỏi lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Sinh viên không tồn tại trong lớp học phần!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return SinhVienResponse.builder().status(ResponseStatus.ERROR).message("Xóa sinh viên khỏi lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public LopHocPhanResponse themLopHocPhan(ThemLopHocPhanInputs inputs) {
        try {
            LopHocPhan _lopHocPhan = lopHocPhanService.themLopHocPhan(inputs);

            return LopHocPhanResponse.builder().status(ResponseStatus.OK).message("Thêm lớp học phần thành công!").data(Arrays.asList(_lopHocPhan)).build();
        } catch (HocPhanIsNotExist ex) {
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Thêm lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Học phần không tồn tại!").build())).build();
        } catch (HocKyNormalIsNotExist ex) {
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Thêm lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Học kỳ không tồn tại!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Thêm lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public LopHocPhanResponse suaLopHocPhan(ThemLopHocPhanInputs inputs, Long id) {
        try {
            LopHocPhan _lopHocPhan = lopHocPhanService.suaLopHocPhan(inputs, id);

            return LopHocPhanResponse.builder().status(ResponseStatus.OK).message("Thêm lớp học phần thành công!").data(Arrays.asList(_lopHocPhan)).build();
        } catch (LopHocPhanIsNotExist ex) {
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Thêm lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lớp học phần không tồn tại!").build())).build();
        } catch (HocPhanIsNotExist ex) {
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Thêm lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Học phần không tồn tại!").build())).build();
        } catch (HocKyNormalIsNotExist ex) {
            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Thêm lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Học kỳ không tồn tại!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return LopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Thêm lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SinhVienResponse themSinhVienVaoLopHocPhan(Long lopHocPhanId, Set<Long> sinhVienIds, Integer nhomThucHanh) {
        try {
            List<SinhVienLopHocPhan> _listSinhVienLopHocPhan = lopHocPhanService.themSinhVienVaoLopHocPhan(lopHocPhanId, sinhVienIds, nhomThucHanh);
            List<SinhVien> _listSinhVien = _listSinhVienLopHocPhan.stream().map(i -> i.getSinhVien()).collect(Collectors.toList());

            return SinhVienResponse.builder().status(ResponseStatus.OK).message("Thêm sinh viên vào lớp học phần thành công!").data(_listSinhVien).build();
        } catch (LopHocPhanIsNotExist ex) {
            return SinhVienResponse.builder().status(ResponseStatus.ERROR).message("Thêm sinh viên vào lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lớp học phần không tồn tại!").build())).build();
        } catch (EnterNhomThucHanh ex) {
            return SinhVienResponse.builder().status(ResponseStatus.ERROR).message("Thêm sinh viên vào lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Chưa chọn nhóm thực hành!").build())).build();
        } catch (ValueOver ex) {
            return SinhVienResponse.builder().status(ResponseStatus.ERROR).message("Thêm sinh viên vào lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Nhóm thực hành không đúng!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return SinhVienResponse.builder().status(ResponseStatus.ERROR).message("Thêm sinh viên vào lớp học phần không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!")

                    .build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TEACHER')")
    public SinhVienLopHocPhanResponse suaDiemSinhVien(SuaSinhVienLopHocPhanInputs inputs) {
        try {
            SinhVienLopHocPhan _sinhVienLopHocPhan = sinhVienLopHocPhanService.suaDiemSinhVien(inputs);

            return SinhVienLopHocPhanResponse.builder().status(ResponseStatus.OK).message("Sửa điểm sinh viên thành công!").data(Arrays.asList(_sinhVienLopHocPhan)).build();
        } catch (SinhVienLopHocPhanIsNotExist ex) {
            return SinhVienLopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Sửa điểm sinh viên không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Sinh viên lớp học phần không tồn tại!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return SinhVienLopHocPhanResponse.builder().status(ResponseStatus.ERROR).message("Sửa điểm sinh viên không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
        }
    }

}
