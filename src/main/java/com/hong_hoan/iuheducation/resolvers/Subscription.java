package com.hong_hoan.iuheducation.resolvers;

import com.hong_hoan.iuheducation.entity.Account;
import com.hong_hoan.iuheducation.entity.Notification;
import com.hong_hoan.iuheducation.resolvers.common.ResponseStatus;
import com.hong_hoan.iuheducation.resolvers.response.notification.NotificationResponse;
import com.hong_hoan.iuheducation.service.AccountService;
import com.hong_hoan.iuheducation.service.NotificationService;
import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class Subscription implements GraphQLSubscriptionResolver {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AccountService accountService;

    public Publisher<NotificationResponse> getNotification(Long sinhVienId) {
        return subscriber -> Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            List<Notification> _listNotification = notificationService.getListNotificationOfStudent(sinhVienId);

            NotificationResponse _notificationResponse = NotificationResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("lấy thông tin thông báo thành công.")
                    .data(_listNotification)
                    .build();

            subscriber.onNext(_notificationResponse);
        }, 0, 1, TimeUnit.SECONDS);
    }

}
