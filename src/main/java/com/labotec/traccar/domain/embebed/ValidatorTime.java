package com.labotec.traccar.domain.embebed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidatorTime {
    @JsonProperty("state_validation")
    private String stateValidation;
    @JsonProperty("value_evaluation")
    private Long valueEvaluation;
}
