package com.hong_hoan.iuheducation.resolvers.common;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private List<String> error_fields;
}
