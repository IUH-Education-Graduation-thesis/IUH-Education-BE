package com.hong_hoan.iuheducation.resolvers.input;

public enum Role {
    TEACHER("TEACHER"),
    ADMIN("ADMIN"),
    STUDENT("STUDENT");

    private final String name;

    Role(String s) {
        name = s;
    }
}
