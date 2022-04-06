package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "phong_hoc")
public class PhongHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String tenPhongHoc;
    @Column(nullable = false)
    private int sucChua;
    private String moTa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "day_nha_id")
    private DayNha dayNha;


    @OneToMany(mappedBy = "phongHoc")
    private List<LichHoc> lichHocs = new ArrayList<>();

}