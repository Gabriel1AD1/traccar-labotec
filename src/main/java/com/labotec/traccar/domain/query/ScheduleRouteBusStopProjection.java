    package com.labotec.traccar.domain.query;

    import lombok.Data;

    import java.util.List;

    @Data
    public class ScheduleRouteBusStopProjection {
        private Long id; // Identificador único de la parada
        private ScheduleBusStopProjection firstBusStop; // Primer paradero
        private ScheduleBusStopProjection secondBusStop; // Segundo paradero
        private List<ScheduleOverviewPolylineProjection> overviewPolylines; // Polilíneas asociadas
        private Long order; // Orden de la parada en la ruta
        private Integer minWaitTime; // Tiempo mínimo de espera
        private Integer maxWaitTime; // Tiempo máximo de espera
        private Integer estimatedTravelTime; // Tiempo estimado de viaje
    }
