package com.labotec.traccar.domain.web.dto.entel.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.enums.STATE;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverDTO {

    @NotNull(message = "First name is required")
    @Size(max = 250, message = "First name must not exceed 250 characters")
    @JsonProperty("first_name")
    private String firstName;

    @NotNull(message = "Document type is required")
    @JsonProperty("document_type")
    private String documentType;

    @NotNull(message = "Document number is required")
    @Size(max = 50, message = "Document number must not exceed 50 characters")
    @JsonProperty("document_number")
    private String documentNumber;

    @NotNull(message = "Status is required")
    @JsonProperty("status")
    private STATE status;
}
