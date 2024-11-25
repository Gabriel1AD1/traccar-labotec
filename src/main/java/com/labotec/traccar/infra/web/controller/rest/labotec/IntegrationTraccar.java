package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.IntegrationTraccarService;
import com.labotec.traccar.domain.web.dto.traccar.LastedInformationVehicle;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "integration-traccar")
@AllArgsConstructor
public class IntegrationTraccar {
    private final IntegrationTraccarService traccarService;

    @PostMapping("/device")
    public ResponseEntity<String> getInformationDevice(@RequestBody LastedInformationVehicle informationVehicle){
        traccarService.processPosition(informationVehicle);
        return ResponseEntity.ok("Ok");
    }
}
