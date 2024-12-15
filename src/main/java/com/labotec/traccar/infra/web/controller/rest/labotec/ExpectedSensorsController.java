package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.services.ExpectedSensorsService;
import com.labotec.traccar.domain.web.labotec.request.create.CreateExpectedSensorsDTO;
import com.labotec.traccar.domain.web.labotec.response.ResponseExpectedSensor;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "expected-sensors")
@AllArgsConstructor
public class ExpectedSensorsController {
    private final ExpectedSensorsService expectedSensorsService;

    @PostMapping()
    public ResponseEntity<Void> create(
            @RequestHeader("userId") Long userId,
            @RequestBody CreateExpectedSensorsDTO dto
            ){
        Long resourceId = expectedSensorsService.create(userId,dto).getId();
        return ResponseEntity.created(URI.create(API_VERSION_V1 + "expected-sensors/"+resourceId)).build();
    }
    @GetMapping("/{deviceId}")
    public ResponseEntity<List<ResponseExpectedSensor>> findALl(
            @RequestHeader("userId") Long userId,
            @PathVariable("deviceId") Long deviceId
    ){
        return ResponseEntity.ok(expectedSensorsService.findAll(userId,deviceId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("userId") Long userId,
            @PathVariable("id") Long resourceId
    ){
        expectedSensorsService.delete(userId,resourceId);
        return ResponseEntity.noContent().build();
    }
}
