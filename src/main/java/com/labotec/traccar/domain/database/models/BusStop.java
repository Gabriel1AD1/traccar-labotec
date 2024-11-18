package com.labotec.traccar.domain.database.models;

import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.enums.STATE_BUS_STOP;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BusStop {
    private Long id;
    private User userId;
    private Company companyId;
    private String name;
    private String latitude;
    private String longitude;
    private STATE status;
    private Instant lastModifiedDate;
    private Instant createdDate;
}