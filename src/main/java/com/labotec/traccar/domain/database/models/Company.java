    package com.labotec.traccar.domain.database.models;

    import lombok.*;

    import java.time.Instant;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class Company {
        private Integer id;
        private String name;
        private String ruc;
        private Byte status;
        private Instant lastModifiedDate;
        private Instant createdDate;
    }
