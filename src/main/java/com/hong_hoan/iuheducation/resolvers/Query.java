package com.hong_hoan.iuheducation.resolvers;

import com.hong_hoan.iuheducation.entity.DayNha;
import com.hong_hoan.iuheducation.repository.DayNhaRepository;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import com.hong_hoan.iuheducation.resolvers.response.day_nha.DayNhaResponse;
import com.hong_hoan.iuheducation.service.DayNhaService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {
    @Autowired
    private DayNhaService dayNhaService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DayNhaResponse findDayNha() {
        List<DayNha> _listDayNha = dayNhaService.getListDayNha();

        return DayNhaResponse.builder()
                .status(ResponseStatus.OK)
                .message("lấy thông tin lớp học thành công.")
                .data(_listDayNha)
                .build();
    }

}
