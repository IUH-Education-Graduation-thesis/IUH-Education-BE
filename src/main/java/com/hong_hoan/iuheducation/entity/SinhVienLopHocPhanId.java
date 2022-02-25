package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class SinhVienLopHocPhanId implements Serializable {

    @Column(name = "sinh_vien_id", nullable = false)
    private long sinhVienId;

    @Column(name = "lop_hoc_phan_id", nullable = false)
    private long lopHocPhanId;

}