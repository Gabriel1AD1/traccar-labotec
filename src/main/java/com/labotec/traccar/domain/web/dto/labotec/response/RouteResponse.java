package com.labotec.traccar.domain.web.dto.labotec.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class RouteResponse {
    private Long id;
    private String name;
}
