package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "nam_hoc")
public class NamHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String moTa;


    @OneToMany(mappedBy = "namHoc", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<HocKy> hocKys = new ArrayList<>();

}