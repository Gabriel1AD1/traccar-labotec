package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "tbl_usuario", schema = "traccar_db")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {
    @Id
    @Column(name = "usuario_id")
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private CompanyEntity companyId;
    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false)
    private Instant createdDate;
    @LastModifiedDate
    @Column(name = "fecha_actualizacion")
    private Instant lastModifiedDate;
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BusStopEntity> busStops;

}
