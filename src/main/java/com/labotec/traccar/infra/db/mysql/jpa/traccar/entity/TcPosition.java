package com.labotec.traccar.infra.db.mysql.jpa.traccar.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.entity.Attributes;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.mapper.AttributesConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tc_positions", schema = "traccar", indexes = {
        @Index(name = "position_deviceid_fixtime", columnList = "deviceid, fixtime")
})
public class TcPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 128)
    @Column(name = "protocol", length = 128)
    private String protocol;

    @NotNull
    @Column(name = "deviceid", nullable = false)
    private Integer deviceid;

    @NotNull
    @Column(name = "servertime", nullable = false)
    private Instant servertime;

    @NotNull
    @Column(name = "devicetime", nullable = false)
    private Instant devicetime;

    @NotNull
    @Column(name = "fixtime", nullable = false)
    private Instant fixtime;

    @NotNull
    @Column(name = "valid", nullable = false)
    private Boolean valid = false;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @NotNull
    @Column(name = "altitude", nullable = false)
    private Float altitude;

    @NotNull
    @Column(name = "speed", nullable = false)
    private Float speed;

    @NotNull
    @Column(name = "course", nullable = false)
    private Float course;

    @Size(max = 512)
    @Column(name = "address", length = 512)
    private String address;

    // Aquí el campo attributes como String que se deserializa
    @Convert(converter = AttributesConverter.class)
    @Column(name = "attributes", length = 4000)
    private Attributes attributes;

    @NotNull
    @Column(name = "accuracy", nullable = false)
    private Double accuracy;

    @Size(max = 4000)
    @Column(name = "network", length = 4000)
    private String network;

    @Size(max = 128)
    @Column(name = "geofenceids", length = 128)
    private String geofenceids;

}
