package com.labotec.traccar.domain.database.models;

import com.labotec.traccar.domain.database.models.optimized.TypeValidation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertValidParams {
    private Long id;
    private TypeValidation typeValidation;
    private Instant createAt;
    private Long deviceId;
    private Long idValidation;
    private String currentValue;
}
