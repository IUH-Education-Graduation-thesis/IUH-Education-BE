package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query(value = "SELECT n.* FROM notification n\n" +
            "JOIN notification_sinh_viens nsv ON nsv.notification_id = n.id\n" +
            "WHERE nsv.sinh_viens_id = 1\n" +
            "ORDER by n.create_date DESC", nativeQuery = true)
    List<Notification> findNotificationOfStudent(Long sinhVienId);

}