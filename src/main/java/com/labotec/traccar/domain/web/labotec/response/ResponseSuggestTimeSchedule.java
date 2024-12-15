package com.labotec.traccar.domain.web.labotec.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseSuggestTimeSchedule {
    @JsonProperty("max_time_for_schedule")
    private Integer maxTimeForSchedule;
    @JsonProperty("min_time_for_schedule")
    private Integer minTimeForSchedule;
}
