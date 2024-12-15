package com.labotec.traccar.domain.web.labotec.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TESTBODY {
    @JsonProperty("test")
    public String esto;
}
