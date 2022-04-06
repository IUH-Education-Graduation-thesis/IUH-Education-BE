package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
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

    @Column(nullable = false)
    private String maHocPhan;
    private String moTa;
    private boolean batBuoc;

    @Column(nullable = false)
    private int soTinChiLyThuyet;
    @Column(nullable = false)
    private int soTinChiThucHanh;

    @ManyToOne
    @JoinColumn(name = "hoc_ky_id")
    private HocKy hocKy;

    @ManyToOne
    @JoinColumn(name = "mon_hoc_id")
    private MonHoc monHoc;

    @OneToMany(mappedBy = "hocPhan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LopHocPhan> lopHocPhans = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "hoc_phan_mon_hoc_tien_quyets",
            joinColumns = @JoinColumn(name = "hoc_phan_id"),
            inverseJoinColumns = @JoinColumn(name = "mon_hocs_id"))
    private Set<MonHoc> monHocTienQuyets = new LinkedHashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "hoc_phan_mon_hoc_song_hanhs",
            joinColumns = @JoinColumn(name = "hoc_phan_id"),
            inverseJoinColumns = @JoinColumn(name = "mon_hocs_id"))
    private Set<MonHoc> monHocSongHanhs = new LinkedHashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "hoc_phan_mon_hoc_truocs",
            joinColumns = @JoinColumn(name = "hoc_phan_id"),
            inverseJoinColumns = @JoinColumn(name = "mon_hocs_id"))
    private Set<MonHoc> monHocTruocs = new LinkedHashSet<>();

}