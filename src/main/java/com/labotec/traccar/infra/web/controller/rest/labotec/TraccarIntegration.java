package com.labotec.traccar.infra.web.controller.rest.labotec;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "integration")
@AllArgsConstructor
public class TraccarIntegration {


    @PostMapping
    public void object (@RequestBody ){

    }

}
