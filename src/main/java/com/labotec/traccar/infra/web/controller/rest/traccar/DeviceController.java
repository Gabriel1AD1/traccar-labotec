package com.labotec.traccar.infra.web.controller.rest.traccar;


import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.VehicleRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.projections.TcDeviceBasicProjection;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.repository.TcDeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "device")
@AllArgsConstructor
public class DeviceController {
    private final TcDeviceRepository deviceRepository;
    private final VehicleRepositoryJpa repositoryJpa;

    @GetMapping()
    public List<TcDeviceBasicProjection> getList(){
        List<Integer> traccarDeviceIds = repositoryJpa.findTraccarDeviceIdBy();
        return deviceRepository.findByIdNotIn(traccarDeviceIds);
    }

}
