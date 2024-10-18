package com.labotec.traccar.domain.database.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    private Integer id;
    private String firstName;
    private Integer documentType;
    private String documentNumber;
    private Byte status;
    private Company company;
}