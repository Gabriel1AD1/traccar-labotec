package com.labotec.traccar.domain.database.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    private Integer id;
    private String name;
    private Byte status;
    private BusStop originBusStop;
    private BusStop destinationBusStop;
    private Company company;
}
