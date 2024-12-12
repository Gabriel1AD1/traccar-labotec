package com.labotec.traccar.domain.database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labotec.traccar.domain.enums.STATE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    private Long id;
    @JsonIgnore
    private User userId;
    @JsonIgnore
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