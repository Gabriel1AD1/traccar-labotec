package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

import java.util.List;

public interface RouteBusStopProjection {
    Long getId();                            // Identificador único
    BusStopProjection getFirstBusStop();               // Primer paradero
    BusStopProjection getSecondBusStop();              // Segundo paradero
    List<OverviewPolylineProjection> getOverviewPolylines(); // Lista de polilíneas asociadas
    Long getOrder();                         // Orden de la parada en la ruta
    Integer getMinWaitTime();                // Tiempo mínimo de espera
    Integer getMaxWaitTime();                // Tiempo máximo de espera
    Integer getEstimatedTravelTime();        // Tiempo estimado de viaje
}