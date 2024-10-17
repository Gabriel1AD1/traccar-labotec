package com.labotec.traccar.infra.db.mysql.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "tbl_lab_chofer", schema = "traccar_db")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_id_chofer", nullable = false)
    private Integer id;

    @Column(name = "lab_nombres", length = 250)
    private String firstName;

    @Column(name = "lab_doc_identidad")
    private Integer documentType;

    @Column(name = "lab_num_identidad", nullable = false, length = 50)
    private String documentNumber;

    @Column(name = "lab_estado", nullable = false)
    private Byte status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_empresa", nullable = false)
    private CompanyEntity company;
}
