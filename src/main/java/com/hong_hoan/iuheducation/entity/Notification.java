package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Date createDate;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private NotiType type;

    private boolean isRead;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "notification_sinh_viens",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "sinh_viens_id"))
    private Set<SinhVien> sinhViens = new LinkedHashSet<>();

}