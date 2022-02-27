package com.hong_hoan.iuheducation.resolvers;

import com.hong_hoan.iuheducation.entity.Account;
import com.hong_hoan.iuheducation.entity.SinhVien;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import com.hong_hoan.iuheducation.resolvers.response.account.LoginData;
import com.hong_hoan.iuheducation.resolvers.response.account.LoginResponse;
import com.hong_hoan.iuheducation.service.AccountService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import kotlin.collections.ArrayDeque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;

@Configuration
public class Mutation implements GraphQLMutationResolver {


    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @PreAuthorize("isAnonymous()")
    public LoginResponse login(String user_name, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user_name, password);

        try {
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(authenticationToken));

            Account _account = accountService.getCurrentAccount();
            SinhVien _sinhVien = _account.getSinhVien();
            String _token = accountService.getToken(_account);

            LoginData _loginData = LoginData.builder()
                    .token(_token)
                    .sinhVien(_sinhVien)
                    .build();

            return LoginResponse.builder()
                    .data(_loginData)
                    .status(ResponseStatus.OK)
                    .message("Đăng nhập thành công.")
                    .build();
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            return LoginResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Đăng nhập không thành công!")
                    .errors(new ArrayList<>() {
                        {
                            add(ErrorResponse.builder().message("Tên tài khoản hoặc mật khẩu không đúng!").build());
                        }
                    })
                    .build();
        }

    }

}
