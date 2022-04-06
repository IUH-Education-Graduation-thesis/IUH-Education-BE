package com.hong_hoan.iuheducation.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "giang_vien")
public class GiangVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String hoTenDem;
    private String ten;
    private String avatar;
    private boolean gioiTinh;
    private String soDienThoai;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "hoc_ham")
    private HocHam hocHam;

    @ManyToOne
    @JoinColumn(name = "chuyen_nganh_id")
    private ChuyenNganh chuyenNganh;

    @ManyToOne
    @JoinColumn(name = "mon_hoc_id")
    private MonHoc monHoc;

    public String hocHamString() {
        return hocHam.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GiangVien giangVien = (GiangVien) o;
        return id != null && Objects.equals(id, giangVien.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}