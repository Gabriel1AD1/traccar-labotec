package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.web.dto.labotec.response.BusStopResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface BusStopRepository extends GenericRepository<BusStop,Long> {

    void validateIdBusStop(@NotNull(message = "First Bus Stop ID is required") @Positive(message = "First Bus Stop ID must be a positive number") Long startBusStopId, Long userId);

    BusStopResponse findByResourceId(Long busStopId);
}
