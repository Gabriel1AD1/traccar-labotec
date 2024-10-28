package com.labotec.traccar.domain.database.models;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
//TODO En ves de usar objetos de dominio usar dto para evitar la sobrecarga de datos
public class BusStop {
    private Integer id;
    private String name;
    private String latitude;
    private String longitude;
    private Byte status;
    private Company company;

    private Instant lastModifiedDate;
    private Instant createdDate;
}