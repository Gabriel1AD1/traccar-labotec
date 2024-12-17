package com.labotec.traccar.domain.embebed;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RangeValue {
    @JsonProperty("init_range")
    @NotNull
    private Double rangeA;
    @JsonProperty("finish_range")
    @NotNull
    private Double rangeB;
}
