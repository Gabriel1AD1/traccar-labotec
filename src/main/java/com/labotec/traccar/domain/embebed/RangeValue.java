package com.labotec.traccar.domain.embebed;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RangeValue {
    @JsonProperty("init_range")
    @NotNull
    private Double rangeA;
    @JsonProperty("finish_range")
    @NotNull
    private Double rangeB;
}
