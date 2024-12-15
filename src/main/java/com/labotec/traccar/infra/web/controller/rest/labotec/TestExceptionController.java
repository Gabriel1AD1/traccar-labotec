package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.domain.web.labotec.request.TESTBODY;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestExceptionController {

    @PostMapping("/api/error")
    public ResponseEntity<Void> testError(@RequestBody TESTBODY dto) throws IllegalAccessException {
        throw new IllegalAccessException("This is a test exception");
    }
}
