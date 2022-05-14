package com.hong_hoan.iuheducation.resolvers.query;

import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import com.hong_hoan.iuheducation.resolvers.input.nam_hoc.FilterNamHocInputs;
import com.hong_hoan.iuheducation.resolvers.response.nam_hoc.FilterNamHocResponse;
import com.hong_hoan.iuheducation.resolvers.response.nam_hoc.PaginationNamHoc;
import com.hong_hoan.iuheducation.service.NamHocService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class NamHocQuery implements GraphQLQueryResolver {
    @Autowired
    private NamHocService namHocService;

    @PreAuthorize("isAuthenticated()")
    public FilterNamHocResponse filterNamHoc(FilterNamHocInputs inputs) {
        try {
            PaginationNamHoc _paginationNamHoc = namHocService.filterNamHoc(inputs);
            return FilterNamHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Lấy thông tin năm học thành công!")
                    .data(Arrays.asList(_paginationNamHoc))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return FilterNamHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Lấy thông tin năm học không thành công!")
                    .errors(Arrays.asList(ErrorResponse.builder()
                            .message("Lỗi hệ thống.")
                            .build()))
                    .build();
        }
    }

}
