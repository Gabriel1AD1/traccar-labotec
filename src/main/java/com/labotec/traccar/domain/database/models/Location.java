package com.labotec.traccar.domain.database.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private Long id;
    private User userId;
    private Company companyId;
    private String name;
    private String latitude;
    private String longitude;
    private BigDecimal radius;
    private Instant lastModifiedDate;
    private Instant createdDate;
}
