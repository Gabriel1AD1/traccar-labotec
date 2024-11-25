    package com.labotec.traccar.domain.web.dto.labotec.request.create;

    import com.fasterxml.jackson.annotation.JsonProperty;
    import com.labotec.traccar.app.Validations.SinglePrimaryPolyline;
    import jakarta.validation.Valid;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Positive;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.List;
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class RouteBusStopCreateDTO
    {
        @NotNull(message = "First Bus Stop ID is required")
        @Positive(message = "First Bus Stop ID must be a positive number")
        @JsonProperty("start_bus_stop_id")
        private Long startBusStopId;
        @NotNull(message = "Second Bus Stop ID is required")
        @Positive(message = "Second Bus Stop ID must be a positive number")
        @JsonProperty("end_bus_stop_id")
        private Long endBusStopId;
        @NotNull(message = "Order is required")
        @Positive(message = "Order must be a positive number")
        @JsonProperty("order")
        private Long order;
        @JsonProperty("min_wait_time")
        private Integer minWaitTime;// En minutos
        @JsonProperty("max_wait_time")
        private Integer maxWaitTime; // En minutos
        @JsonProperty("estimated_travel_time")
        private Integer estimatedTravelTime; // En minutos
        @NotNull(message = "List of Polylines is required")
        @JsonProperty("polylines")
        private List<PolylineCreateDTO> polylines;
    }