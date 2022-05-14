package com.hong_hoan.iuheducation.resolvers.mutation;

import com.hong_hoan.iuheducation.entity.LichHoc;
import com.hong_hoan.iuheducation.exception.GroupPraticeOver;
import com.hong_hoan.iuheducation.exception.LichHocIsNotExistException;
import com.hong_hoan.iuheducation.exception.LopHocPhanIsNotExist;
import com.hong_hoan.iuheducation.exception.ValueOver;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import com.hong_hoan.iuheducation.resolvers.input.lich_hoc.ThemLichHocInputs;
import com.hong_hoan.iuheducation.resolvers.response.lich_hoc.LichHocResponse;
import com.hong_hoan.iuheducation.service.LichHocService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class LichHocMutation implements GraphQLMutationResolver {
    @Autowired
    private LichHocService lichHocService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public LichHocResponse suaLichHoc(ThemLichHocInputs inputs, Long id) {
        try {
            LichHoc _lichHoc = lichHocService.suaLichHoc(inputs, id);

            return LichHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Sửa lịch học thành công.")
                    .data(Arrays.asList(_lichHoc))
                    .build();

        } catch (LichHocIsNotExistException ex) {
            return LichHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sửa lịch học không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lịch học không tồn tại!")
                            .build()))
                    .build();
        } catch (LopHocPhanIsNotExist ex) {
            return LichHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sửa lịch học không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lớp học phần không tồn tại!")
                            .build()))
                    .build();

        } catch (GroupPraticeOver ex) {
            return LichHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sửa lịch học không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Nhóm thực hành lớp hơn số nhóm thực hành!")
                            .build()))
                    .build();

        } catch (ValueOver ex) {
            return LichHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sửa lịch học không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Tiết học bắt đầu lớn hơn tiết học kết thúc!")
                            .build()))
                    .build();

        } catch (Exception ex) {
            ex.printStackTrace();
            return LichHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Sửa lịch học không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();

        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public LichHocResponse themLichHoc(ThemLichHocInputs inputs) {
        try {
            LichHoc _lichHoc = lichHocService.themLichHoc(inputs);

            return LichHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Thêm lịch học thành công.")
                    .data(Arrays.asList(_lichHoc))
                    .build();

        } catch (LopHocPhanIsNotExist ex) {
            return LichHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm lịch học không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lớp học phần không tồn tại!")
                            .build()))
                    .build();

        } catch (GroupPraticeOver ex) {
            return LichHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm lịch học không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Nhóm thực hành lớp hơn số nhóm thực hành!")
                            .build()))
                    .build();

        } catch (ValueOver ex) {
            return LichHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm lịch học không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Tiết học bắt đầu lớn hơn tiết học kết thúc!")
                            .build()))
                    .build();

        } catch (Exception ex) {
            ex.printStackTrace();
            return LichHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm lịch học không thành công.")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();

        }
    }


}
