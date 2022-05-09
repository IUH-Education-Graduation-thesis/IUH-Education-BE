package com.hong_hoan.iuheducation.resolvers.response.notification;

import com.hong_hoan.iuheducation.entity.Notification;
import com.hong_hoan.iuheducation.entity.SinhVien;
import com.hong_hoan.iuheducation.resolvers.common.ErrorResponse;
import com.hong_hoan.iuheducation.resolvers.common.ResponseInterface;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private ResponseStatus status;
    private List<ErrorResponse> errors;
    private String message;
    private List<Notification> data;
}
