package com.labotec.traccar.domain.database.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OverviewPolyline {
    private Long id;
    @JsonIgnore
    private RouteBusStop routeBusStop; // Referencia a tabla intermedia
    private String polyline;
    private Boolean isPrimary;
    @JsonIgnore
    private Instant lastModifiedDate;
    @JsonIgnore
    private Instant createdDate;
}
