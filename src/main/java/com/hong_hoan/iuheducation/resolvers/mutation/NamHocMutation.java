package com.hong_hoan.iuheducation.resolvers.mutation;

import com.hong_hoan.iuheducation.entity.NamHoc;
import com.hong_hoan.iuheducation.exception.NamHocIsNotExist;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import com.hong_hoan.iuheducation.resolvers.input.nam_hoc.ThemNamHocInputs;
import com.hong_hoan.iuheducation.resolvers.response.nam_hoc.NamHocResponse;
import com.hong_hoan.iuheducation.service.NamHocService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class NamHocMutation implements GraphQLMutationResolver {
    @Autowired
    private NamHocService namHocService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public NamHocResponse xoaNamHocs(List<Long> ids) {
        try {
            List<NamHoc> _listNamHoc = namHocService.xoaNamHoc(ids);

            return NamHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xóa năm học thành công!")
                    .data(_listNamHoc)
                    .build();
        } catch (NamHocIsNotExist ex) {
            return NamHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa năm học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Năm học không tồn tại!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return NamHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa năm học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống")
                            .build()))
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public NamHocResponse suaNamHoc(ThemNamHocInputs inputs, Long id) {
        try {
            NamHoc _namHoc = namHocService.suaNamHoc(inputs, id);

            return NamHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Thêm năm học thành công!")
                    .data(Arrays.asList(_namHoc))
                    .build();
        } catch (NamHocIsNotExist ex) {
            return NamHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm năm học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Năm học không tồn tại!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return NamHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm năm học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống")
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
                    .message("Thêm năm học thành công!")
                    .data(Arrays.asList(_namHoc))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return NamHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm năm học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống")
                            .build()))
                    .build();

        }
    }

}
