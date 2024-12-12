package com.labotec.traccar.infra.db.mysql.jpa.traccar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labotec.traccar.infra.web.controller.rest.traccar.helper.Hashing;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tc_users", schema = "traccar")
public class TcUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 128)
    @NotNull
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Size(max = 128)
    @NotNull
    @Column(name = "email", nullable = false, length = 128)
    private String email;

    @Size(max = 128)
    @Column(name = "hashedpassword", length = 128)
    private String hashedpassword;

    @Size(max = 128)
    @Column(name = "salt", length = 128)
    private String salt;

    @NotNull
    @ColumnDefault("b'0'")
    @Column(name = "readonly", nullable = false)
    private Boolean readonly = false;

    @Column(name = "administrator")
    private Boolean administrator;

    @Size(max = 128)
    @Column(name = "map", length = 128)
    private String map;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "zoom", nullable = false)
    private Integer zoom;

    @Size(max = 4000)
    @Column(name = "attributes", length = 4000)
    private String attributes;

    @Size(max = 128)
    @Column(name = "coordinateformat", length = 128)
    private String coordinateformat;

    @ColumnDefault("b'0'")
    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "expirationtime")
    private Instant expirationtime;

    @ColumnDefault("-1")
    @Column(name = "devicelimit")
    private Integer devicelimit;

    @ColumnDefault("0")
    @Column(name = "userlimit")
    private Integer userlimit;

    @ColumnDefault("b'0'")
    @Column(name = "devicereadonly")
    private Boolean devicereadonly;

    @Size(max = 128)
    @Column(name = "phone", length = 128)
    private String phone;

    @ColumnDefault("b'0'")
    @Column(name = "limitcommands")
    private Boolean limitcommands;

    @Size(max = 128)
    @Column(name = "login", length = 128)
    private String login;

    @Size(max = 512)
    @Column(name = "poilayer", length = 512)
    private String poilayer;

    @ColumnDefault("b'0'")
    @Column(name = "disablereports")
    private Boolean disablereports;

    @ColumnDefault("b'0'")
    @Column(name = "fixedemail")
    private Boolean fixedemail;

    @Size(max = 64)
    @Column(name = "totpkey", length = 64)
    private String totpkey;

    @Column(name = "isAdministratorCompany")
    private Boolean isAdministratorCompany;
    @ColumnDefault("b'0'")
    @Column(name = "temporary")
    private Boolean temporary;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private TcCompany tcCompany;
    public void setPassword(String password) {
        Hashing.HashingResult hashingResult = Hashing.createHash(password);
        this.hashedpassword = hashingResult.getHash();
        this.salt = hashingResult.getSalt();
    }
}