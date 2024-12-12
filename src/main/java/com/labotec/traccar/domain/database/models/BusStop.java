package com.labotec.traccar.domain.database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labotec.traccar.domain.enums.STATE;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BusStop {
    private Long id;
    @JsonIgnore
    private User userId;
    @JsonIgnore
    private Company companyId;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private STATE status;
    @JsonIgnore
    private Instant lastModifiedDate;
    @JsonIgnore
    private Instant createdDate;

    public BusStop(Long id) {
        this.id = id;
    }
}