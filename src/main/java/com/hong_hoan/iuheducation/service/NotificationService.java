package com.hong_hoan.iuheducation.service;

import com.hong_hoan.iuheducation.entity.Account;
import com.hong_hoan.iuheducation.entity.Notification;
import com.hong_hoan.iuheducation.exception.NotificationIsNotExist;
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

    public Notification suaNotification(Long id, boolean isRead) {
        Notification _notification = notificationRepository.getById(id);

        if (_notification == null) {
            throw new NotificationIsNotExist();
        }

        _notification.setRead(isRead);

        Notification _notificationRes = notificationRepository.saveAndFlush(_notification);

        return _notification;
    }

}
