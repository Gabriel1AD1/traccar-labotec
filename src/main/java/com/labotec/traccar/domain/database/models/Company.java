    package com.labotec.traccar.domain.database.models;

    import lombok.*;

    import java.time.Instant;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public class Company {
        private Long companyId;
        private Instant lastModifiedDate;
        private Instant createdDate;
    }
