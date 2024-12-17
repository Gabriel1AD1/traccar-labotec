package com.labotec.traccar.infra.web.controller.rest.traccar;


import com.labotec.traccar.domain.web.traccar.DeviceRequestDTO;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.VehicleRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.projections.TcDeviceBasicProjection;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.repository.TcDeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "device")
@AllArgsConstructor
public class DeviceController {
    private final TcDeviceRepository deviceRepository;
    private final VehicleRepositoryJpa repositoryJpa;
    private final VehicleMonitor vehicleMonitor;

    @GetMapping()
    public List<TcDeviceBasicProjection> getList(){
        List<Integer> traccarDeviceIds = repositoryJpa.findTraccarDeviceIdBy();
        return deviceRepository.findByIdNotIn(traccarDeviceIds);
    }

    @PostMapping()
    public String getList(@RequestBody DeviceRequestDTO deviceRequestDTO) {
        vehicleMonitor.processPositionData(deviceRequestDTO);
        vehicleMonitor.processDoorStatus(deviceRequestDTO);
        System.out.println(deviceRequestDTO);

        return "Ok";
    }


}
