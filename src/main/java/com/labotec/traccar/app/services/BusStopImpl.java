package com.labotec.traccar.app.services;

import com.labotec.traccar.app.usecase.ports.input.BusStopRepository;
import com.labotec.traccar.app.usecase.ports.out.GenericCrudService;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.web.dto.BusStopDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BusStopImpl implements GenericCrudService<BusStop ,BusStopDTO , Integer> {
    private final BusStopRepository busStopRepository ;

    @Override
    public BusStop create(BusStopDTO entityDto) {
        return busStopRepository.create(entityDto);
    }

    @Override
    public BusStop findById(Integer integer) {
        return busStopRepository.findById(integer);
    }

    @Override
    public Iterable<BusStop> findAll() {
        return busStopRepository.findAll();
    }

    @Override
    public BusStop update(BusStopDTO entityDto, Integer integer) {
        return busStopRepository.update(entityDto,integer);
    }

    @Override
    public void deleteById(Integer integer) {
        busStopRepository.deleteById(integer);
    }
}
