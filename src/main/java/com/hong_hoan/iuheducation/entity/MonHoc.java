package com.hong_hoan.iuheducation.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mon_hoc")
public class MonHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String ten;
    private String moTa;
    private int soTinChiLyThuyet;
    private int soTinChiThucHanh;

    @ManyToOne
    @JoinColumn(name = "khoa_vien_id")
    private KhoaVien khoaVien;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MonHoc monHoc = (MonHoc) o;
        return id != null && Objects.equals(id, monHoc.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}