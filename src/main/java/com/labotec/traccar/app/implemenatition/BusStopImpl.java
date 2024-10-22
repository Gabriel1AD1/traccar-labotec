package com.labotec.traccar.app.implemenatition;

import com.labotec.traccar.app.mapper.BusStopModelMapper;
import com.labotec.traccar.app.usecase.ports.input.BusStopRepository;
import com.labotec.traccar.app.usecase.ports.input.CompanyRepository;
import com.labotec.traccar.app.usecase.ports.out.BusStopService;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.web.dto.BusStopDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BusStopImpl implements BusStopService {
    private final BusStopRepository busStopRepository ;
    private final CompanyRepository companyRepository;
    private final BusStopModelMapper busStopModelMapper;

    @Override
    public BusStop create(BusStopDTO entityDto) {
        // Get Company by id
        Company company = companyRepository.findById(entityDto.getCompanyId());
        BusStop busStop = busStopModelMapper.INSTANCE.toBusStopModel(entityDto);
        busStop.setCompany(company);
        return busStopRepository.create(busStop);
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
        // Get Company by id
        BusStop busStop = busStopRepository.findById(integer);
        Company company = companyRepository.findById(entityDto.getCompanyId());
        BusStop busStopSave = busStopModelMapper.toBusStopModel(entityDto);
        busStop.setId(busStop.getId());
        busStop.setCompany(company);
        return busStopRepository.update(busStopSave);
    }

    @Override
    public void deleteById(Integer integer) {
        busStopRepository.deleteById(integer);
    }
}
