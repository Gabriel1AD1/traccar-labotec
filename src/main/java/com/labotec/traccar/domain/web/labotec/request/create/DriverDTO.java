package com.labotec.traccar.domain.web.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @NotNull(message = "Last name is required")
    @Size(max = 250, message = "Last name must not exceed 250 characters")
    @JsonProperty("last_name")
    private String lastName;
    @NotNull(message = "Document type is required")
    @JsonProperty("document_type")
    private String documentType;
    @NotNull(message = "Document number is required")
    @Size(max = 50, message = "Document number must not exceed 50 characters")
    @JsonProperty("document_number")
    private String documentNumber;
    @NotNull(message = "Phone number is required")
    @Size(max = 50)
    @JsonProperty("phone")
    private String phone;
    @NotNull(message = "Status is required")
    @JsonProperty("status")
    private STATE status;
}
