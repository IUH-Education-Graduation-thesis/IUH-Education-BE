package com.hong_hoan.iuheducation.resolvers.mutation;

import com.hong_hoan.iuheducation.entity.HocKyNormal;
import com.hong_hoan.iuheducation.exception.NamHocIsNotExist;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import com.hong_hoan.iuheducation.resolvers.input.hoc_ky_normal.ThemHocKyNormalInputs;
import com.hong_hoan.iuheducation.resolvers.response.hoc_ky_normal.HocKyNormalResponse;
import com.hong_hoan.iuheducation.service.HocKyNormalService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class HocKyNormalMutation implements GraphQLMutationResolver {
    @Autowired
    private HocKyNormalService hocKyNormalService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public HocKyNormalResponse themHocKyNormal(ThemHocKyNormalInputs inputs) {
        try {
            HocKyNormal _hocKyNormal = hocKyNormalService.themHocKyNormal(inputs);

            return HocKyNormalResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Thêm học kỳ thành công!")
                    .data(Arrays.asList(_hocKyNormal))
                    .build();
        } catch (NamHocIsNotExist ex) {
            return HocKyNormalResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm học kỳ không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Năm học không tồn tại!")
                            .build()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();

            return HocKyNormalResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm học kỳ không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống!")
                            .build()))
                    .build();
        }
    }

}
