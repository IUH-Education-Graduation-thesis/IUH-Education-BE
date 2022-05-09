package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.Account;
import com.hong_hoan.iuheducation.entity.Notification;
import com.hong_hoan.iuheducation.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getListNotificationOfStudent(Long sinhVienId) {

        List<Notification> _listNotification = notificationRepository.findNotificationOfStudent(sinhVienId);

        return _listNotification;
    }

}
