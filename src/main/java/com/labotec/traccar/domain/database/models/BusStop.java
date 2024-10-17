package com.labotec.traccar.domain.database.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusStop {
    private Integer id;
    private String name;
    private String latitude;
    private String longitude;
    private Byte status;
    private Company company;
}