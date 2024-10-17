package com.labotec.traccar.domain.database.models;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private Integer id;
    private String name;
    private String latitude;
    private String longitude;
    private BigDecimal radius;
    private Company company;
}
