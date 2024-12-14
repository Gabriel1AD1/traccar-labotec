package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.services.AlertService;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseAlert;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notify")
@AllArgsConstructor
public class NotificationController {

    private AlertService alertService;
    @GetMapping()
    public ResponseEntity<List<ResponseAlert>> findAll(@RequestHeader("userId")Long userId){
        return ResponseEntity.ok(alertService.findAll(userId));
    }
    @GetMapping("/top/10")
    public ResponseEntity<List<ResponseAlert>> findAllTop(@RequestHeader("userId")Long userId){
        return ResponseEntity.ok(alertService.findAllTop10(userId));
    }
}
