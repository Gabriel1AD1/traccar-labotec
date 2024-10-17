package com.labotec.traccar.domain.database.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private Integer id;
    private String name;
    private String ruc;
    private Byte status;
}
