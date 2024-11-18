package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.BusStopModelMapper;
import com.labotec.traccar.app.usecase.ports.input.repository.BusStopRepository;
import com.labotec.traccar.app.usecase.ports.input.repository.CompanyRepository;
import com.labotec.traccar.app.usecase.ports.input.repository.UserRepository;
import com.labotec.traccar.app.usecase.ports.out.BusStopService;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.web.dto.entel.create.BusStopDTO;
import com.labotec.traccar.domain.web.dto.entel.update.BusStopUpdateDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor

public class BusStopImpl implements BusStopService {
    private final BusStopRepository busStopRepository ;
    private final CompanyRepository companyRepository;
    private final BusStopModelMapper busStopModelMapper;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(BusStopImpl.class);

    @Override
    public BusStop create(BusStopDTO busStopDTO, Long userId) {
        User userFindByUserId = userRepository.findByUserId(userId);
        BusStop busStop = busStopModelMapper.toBusStopModel(busStopDTO);
        busStop.setUserId(userFindByUserId);
        busStop.setCompanyId(userFindByUserId.getCompanyId());
        busStop.setStatus(STATE.ACTIVO);
        logger.info("Paradero creado con exitó");
        return busStopRepository.create(busStop);
    }

    @Override
    public BusStop findById(Long resourceId, Long userId) {
        return busStopRepository.findById(resourceId,userId);
    }

    @Override
    public Iterable<BusStop> findAll(Long userId) {
        return busStopRepository.findAll(userId);
    }

    @Override
    public BusStop update(BusStopUpdateDTO busStopUpdateDTO, Long resourceId, Long userId) {
        BusStop findByResourceAndUserId = busStopRepository.findById(resourceId,userId);
        findByResourceAndUserId.setName(busStopUpdateDTO.getName());
        findByResourceAndUserId.setLongitude(busStopUpdateDTO.getLongitude());
        findByResourceAndUserId.setLatitude(busStopUpdateDTO.getLatitude());
        findByResourceAndUserId .setStatus(busStopUpdateDTO.getStatus());
        return busStopRepository.update(findByResourceAndUserId);
    }

    @Override
    public void deleteById(Long resourceId, Long userId) {
        busStopRepository.deleteById(resourceId,userId);
    }
}
