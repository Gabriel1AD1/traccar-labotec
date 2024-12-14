package com.labotec.traccar.domain.embebed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidatorTime {
    @JsonProperty("expected_value")
    private String expectedValue;
    @JsonProperty("value_evaluation")
    private String valueEvaluation;
}
