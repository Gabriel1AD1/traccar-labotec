package com.labotec.traccar.domain.database.models;

import com.labotec.traccar.domain.enums.STATE;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    private Long id;
    private User userId;
    private Company companyId;
    private String phone;
    private String firstName;
    private String lastName;
    private String documentType;
    private String documentNumber;
    private STATE status;
    private Instant lastModifiedDate;
    private Instant createdDate;
}