package com.hong_hoan.iuheducation.resolvers.input;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CreateAccountInput {
    private String username;
    private String password;
    private List<Role> roles;
}
