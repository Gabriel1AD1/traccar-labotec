package com.labotec.traccar.domain.web.dto.labotec.response;
import com.labotec.traccar.domain.enums.STATE;
import lombok.Data;

@Data
public class BusStopResponse {
    private Long id;
    private Long userId;
    private Long companyId;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private STATE status;
}
