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

    @Column(nullable = false)
    private String maMonHoc;

    @Column(nullable = false)
    private String ten;
    private String moTa;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "khoa_vien_id")
    private KhoaVien khoaVien;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "mon_hoc_giang_vien",
            joinColumns = @JoinColumn(name = "mon_hoc_id"),
            inverseJoinColumns = @JoinColumn(name = "giang_vien_id"))
    private Set<GiangVien> giangViens = new LinkedHashSet<>();

    @OneToMany(mappedBy = "monHoc", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<HocPhan> hocPhans = new LinkedHashSet<>();

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