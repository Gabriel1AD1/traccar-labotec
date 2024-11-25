    package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

    import com.labotec.traccar.domain.enums.STATE;
    import jakarta.persistence.*;
    import lombok.*;
    import org.springframework.data.annotation.CreatedDate;
    import org.springframework.data.annotation.LastModifiedDate;
    import org.springframework.data.jpa.domain.support.AuditingEntityListener;

    import java.time.Instant;
    @Data
    @Builder
    @Entity
    @Table(
            name = "tbl_chofer",
            schema = "traccar_db",
            indexes = {
                    @Index(name = "idx_id_company", columnList = "id, empresa_id"),
                    @Index(name = "idx_id_user", columnList = "id, usuario_id"),
                    @Index(name = "idx_company", columnList = "empresa_id"),
                    @Index(name = "idx_user", columnList = "usuario_id")
            }
    )
    @NoArgsConstructor
    @AllArgsConstructor
    @EntityListeners(AuditingEntityListener.class)
    public class DriverEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "usuario_id")
        private UserEntity userId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "empresa_id")
        private CompanyEntity companyId;

        @Column(name = "nombres", length = 250)
        private String firstName;
        @Column(name = "apellido", length = 250)
        private String lastName;

        @Column(name = "identidad")
        private String documentType;

        @Column(name = "numero_identidad", nullable = false, length = 50)
        private String documentNumber;
        @Column(name = "telefono")
        private String phone;
        @Column(name = "estado", nullable = false)
        private STATE status;

        @CreatedDate
        @Column(name = "fecha_creacion", updatable = false)
        private Instant createdDate;

        @LastModifiedDate
        @Column(name = "fecha_actualizacion")
        private Instant lastModifiedDate;
    }
