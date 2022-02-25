package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "day_nha")
public class DayNha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String tenDayNha;
    private String moTa;

    @OneToMany(mappedBy = "dayNha")
    private List<PhongHoc> phongHocs = new ArrayList<>();

}