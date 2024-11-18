package com.labotec.traccar.domain.database.models;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.CompanyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Long userId;
    private Company companyId;
    private Instant createdDate;
    private Instant lastModifiedDate;

}
