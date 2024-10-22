package com.labotec.traccar.infra.db.mysql.jpa.traccar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tc_commands", schema = "traccar")
public class TcCommand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 4000)
    @NotNull
    @Column(name = "description", nullable = false, length = 4000)
    private String description;

    @Size(max = 128)
    @NotNull
    @Column(name = "type", nullable = false, length = 128)
    private String type;

    @NotNull
    @Column(name = "textchannel", nullable = false)
    private Boolean textchannel = false;

    @Size(max = 4000)
    @NotNull
    @Column(name = "attributes", nullable = false, length = 4000)
    private String attributes;

}