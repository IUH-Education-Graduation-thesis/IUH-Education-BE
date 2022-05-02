package com.hong_hoan.iuheducation.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hoc_ky")
public class HocKy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private int thuTu;
    private String moTa;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "hoc_phan_hoc_ky",
            joinColumns = @JoinColumn(name = "hoc_ky_id"),
            inverseJoinColumns = @JoinColumn(name = "hoc_phan_id"))
    private Set<HocPhan> hocPhans = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "khoa_id")
    private Khoa khoa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HocKy hocKy = (HocKy) o;
        return id != null && Objects.equals(id, hocKy.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}