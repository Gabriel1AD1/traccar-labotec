package com.labotec.traccar.domain.web.labotec.response;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class RouteResponse {
    private Long id;
    private String name;
}
