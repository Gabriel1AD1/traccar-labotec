package com.labotec.traccar.infra.db.mysql.jpa.traccar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "databasechangelog", schema = "traccar")
public class Databasechangelog {
    @Size(max = 255)
    @NotNull
    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Size(max = 255)
    @NotNull
    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Size(max = 255)
    @NotNull
    @Column(name = "FILENAME", nullable = false)
    private String filename;

    @NotNull
    @Column(name = "DATEEXECUTED", nullable = false)
    private Instant dateexecuted;

    @NotNull
    @Column(name = "ORDEREXECUTED", nullable = false)
    private Integer orderexecuted;

    @Size(max = 10)
    @NotNull
    @Column(name = "EXECTYPE", nullable = false, length = 10)
    private String exectype;

    @Size(max = 35)
    @Column(name = "MD5SUM", length = 35)
    private String md5sum;

    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

    @Size(max = 255)
    @Column(name = "COMMENTS")
    private String comments;

    @Size(max = 255)
    @Column(name = "TAG")
    private String tag;

    @Size(max = 20)
    @Column(name = "LIQUIBASE", length = 20)
    private String liquibase;

    @Size(max = 255)
    @Column(name = "CONTEXTS")
    private String contexts;

    @Size(max = 255)
    @Column(name = "LABELS")
    private String labels;

    @Size(max = 10)
    @Column(name = "DEPLOYMENT_ID", length = 10)
    private String deploymentId;

}