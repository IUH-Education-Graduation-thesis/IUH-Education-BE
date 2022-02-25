package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "hoc_phan")
public class HocPhan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String maHocPhan;
    private String moTa;
    private boolean batBuoc;

    @ManyToOne
    @JoinColumn(name = "hoc_ky_id")
    private HocKy hocKy;

    @ManyToOne
    @JoinColumn(name = "mon_hoc_id")
    private MonHoc monHoc;

    @OneToMany(mappedBy = "hocPhan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MonHoc> monHocTienQuyet = new ArrayList<>();

    @OneToMany(mappedBy = "hocPhan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MonHoc> monHocSongHanh = new ArrayList<>();

    @OneToMany(mappedBy = "hocPhan", orphanRemoval = true)
    private List<MonHoc> monHocTruoc = new ArrayList<>();

}