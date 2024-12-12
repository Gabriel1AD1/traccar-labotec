package com.labotec.traccar.domain.database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labotec.traccar.domain.enums.STATE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private Long id;
    @JsonIgnore
    private User userId;
    @JsonIgnore
    private Company companyId;
    private String name;
    private String description;
    private STATE state;
    private BusStop busStopAssociate;
    private String latitude;
    private String longitude;
    private BigDecimal radius;
    @JsonIgnore
    private Instant lastModifiedDate;
    @JsonIgnore
    private Instant createdDate;
}
