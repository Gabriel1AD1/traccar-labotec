package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.model.BusStopModelMapper;
import com.labotec.traccar.app.ports.input.repository.BusStopRepository;
import com.labotec.traccar.app.ports.input.repository.CompanyRepository;
import com.labotec.traccar.app.ports.input.repository.UserRepository;
import com.labotec.traccar.app.ports.out.services.BusStopService;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.BusStopUpdateListDTO;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.web.labotec.request.create.BusStopCreateDTO;
import com.labotec.traccar.domain.web.labotec.request.update.BusStopUpdateDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class BusStopServiceI implements BusStopService {
    private final BusStopRepository busStopRepository ;
    private final CompanyRepository companyRepository;
    private final BusStopModelMapper busStopModelMapper;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(BusStopServiceI.class);

    @Override
    public BusStop create(BusStopCreateDTO busStopCreateDTO, Long userId) {
        User userFindByUserId = userRepository.findByUserId(userId);
        BusStop busStop = busStopModelMapper.toBusStopModel(busStopCreateDTO);
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

    @Override
    public List<Long> createBusStopList(List<BusStopCreateDTO> busStopListCreateDTO, Long userId) {
        List<Long> idBusStops = new ArrayList<>();
        for (BusStopCreateDTO busStopCreateDTO : busStopListCreateDTO){
            User userFindByUserId = userRepository.findByUserId(userId);
            BusStop busStop = busStopModelMapper.toBusStopModel(busStopCreateDTO);
            busStop.setUserId(userFindByUserId);
            busStop.setCompanyId(userFindByUserId.getCompanyId());
            busStop.setStatus(STATE.ACTIVO);
            idBusStops.add(busStopRepository.create(busStop).getId());
        }
        return idBusStops;
    }

    @Override
    public void updateListBusStop(List<BusStopUpdateListDTO> busStopUpdateListDTO,Long userId) {
        for(BusStopUpdateListDTO updateListDTO : busStopUpdateListDTO){
            BusStop busStop = busStopRepository.findById(updateListDTO.getId(),userId);
            busStop.setName(updateListDTO.getName());
            busStop.setLongitude(updateListDTO.getLongitude());
            busStop.setLatitude(updateListDTO.getLatitude());
            busStop.setStatus(STATE.ACTIVO);
            busStop.setDescription(updateListDTO.getDescription());
            busStopRepository.update(busStop);
        }
    }

}
