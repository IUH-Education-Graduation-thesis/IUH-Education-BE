package com.hong_hoan.iuheducation.resolvers.mutation;

import com.hong_hoan.iuheducation.entity.HocKy;
import com.hong_hoan.iuheducation.exception.HocKyIsNotExist;
import com.hong_hoan.iuheducation.exception.HocPhanIsNotExist;
import com.hong_hoan.iuheducation.exception.KhoaHocIsNotExist;
import com.hong_hoan.iuheducation.exception.NamHocIsNotExist;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import com.hong_hoan.iuheducation.resolvers.input.hoc_ky.ThemHocKyInputs;
import com.hong_hoan.iuheducation.resolvers.response.hoc_ky.HocKyResponse;
import com.hong_hoan.iuheducation.service.HocKyService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class HocKyMutation implements GraphQLMutationResolver {

    @Autowired
    private HocKyService hocKyService;

    /*
        hoc ky
        ======================================================================
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public HocKyResponse themHocPhanVaoHocKy(List<Long> hocPhanIds, Long hocKyId) {
        try {
            HocKy _hocHocKy = hocKyService.themHocPhanVaoHocKy(hocPhanIds, hocKyId);

            return HocKyResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(Arrays.asList(_hocHocKy))
                    .message("Them hoc phan vao hoc ky thanh cong")
                    .build();
        } catch (HocKyIsNotExist ex) {
            return HocKyResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Them hoc phan vao hoc ky không thanh cong")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Học kỳ không tồn tại!")
                            .build()))
                    .build();
        } catch (HocPhanIsNotExist ex) {
            return HocKyResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Them hoc phan vao hoc ky không thanh cong")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Học phần không tồn tại!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return HocKyResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Them hoc phan vao hoc ky không thanh cong")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public HocKyResponse xoaHocKys(List<Long> ids) {
        try {
            List<HocKy> _hoKys = hocKyService.xoaHocKys(ids);
            return HocKyResponse.builder().status(ResponseStatus.OK).data(_hoKys).message("Xóa học kỳ thành công.").build();
        } catch (HocKyIsNotExist ex) {
            return HocKyResponse.builder().status(ResponseStatus.ERROR).message("Xóa học kỳ không thành công!").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("id")).message("Hoc ky không tồn tại!").build())).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return HocKyResponse.builder().status(ResponseStatus.ERROR).message("Xóa học kỳ không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public HocKyResponse suaHocKy(ThemHocKyInputs inputs, Long id) {
        try {
            HocKy _hocKy = hocKyService.suaHocKy(inputs, id);

            return HocKyResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Sửa học kỳ thành công.")
                    .data(Arrays.asList(_hocKy))
                    .build();
        } catch (HocKyIsNotExist ex) {
            return HocKyResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sửa học kỳ không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Học kỳ không tồn tại!")
                            .build()))
                    .build();
        } catch (KhoaHocIsNotExist ex) {
            return HocKyResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sửa học kỳ không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Khóa hoc không tồn tại!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return HocKyResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sửa học kỳ không thành công.")
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

            return HocKyResponse.builder().status(ResponseStatus.OK).message("Thêm học kỳ thành công!").data(Arrays.asList(_hocKy)).build();
        } catch (NumberFormatException ex) {
            return HocKyResponse.builder().status(ResponseStatus.ERROR).message("Thêm học kỳ không thành công!").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("namHocId")).message("Năm học không tồn tại!").build())).build();
        } catch (NamHocIsNotExist ex) {
            return HocKyResponse.builder().status(ResponseStatus.ERROR).message("Thêm học kỳ không thành công!").errors(Arrays.asList(ErrorResponse.builder().error_fields(Arrays.asList("namHocId")).message("Năm học không tồn tại!").build())).build();
        } catch (Exception ex) {
            return HocKyResponse.builder().status(ResponseStatus.ERROR).message("Thêm học kỳ không thành công!").errors(Arrays.asList(ErrorResponse.builder().message("Lỗi hệ thống!").build())).build();
        }
    }


}
