package com.hong_hoan.iuheducation.resolvers.mutation;

import com.hong_hoan.iuheducation.entity.GiangVien;
import com.hong_hoan.iuheducation.entity.MonHoc;
import com.hong_hoan.iuheducation.exception.GiangVienIsNotExistException;
import com.hong_hoan.iuheducation.exception.KhoaVienIsNotExistException;
import com.hong_hoan.iuheducation.exception.MonHocIsExistException;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import com.hong_hoan.iuheducation.resolvers.input.mon_hoc.ThemMonHocInputs;
import com.hong_hoan.iuheducation.resolvers.response.giang_vien.GiangVienResponse;
import com.hong_hoan.iuheducation.resolvers.response.mon_hoc.MonHocRespone;
import com.hong_hoan.iuheducation.service.MonHocService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class MonHocMutation implements GraphQLMutationResolver {

    @Autowired
    private MonHocService monHocService;


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public GiangVienResponse xoaGiangViensOfMonHoc(List<Long> giangVienIds, Long monHocId) {
        try {
            List<GiangVien> _listGiangVien = monHocService.xoaGiangVienOfMonHoc(giangVienIds, monHocId);

            return GiangVienResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xóa giảng viên khỏi môn học thành công!")
                    .data(_listGiangVien)
                    .build();
        } catch (GiangVienIsNotExistException ex) {
            return GiangVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa giảng viên khỏi môn học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Không có giảng viên nào đươc tìm thấy trong danh sách!")
                            .build()))
                    .build();
        } catch (MonHocIsExistException ex) {
            return GiangVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa giảng viên khỏi môn học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Không tìm thấy môn học!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return GiangVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa giảng viên khỏi môn học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
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
}
