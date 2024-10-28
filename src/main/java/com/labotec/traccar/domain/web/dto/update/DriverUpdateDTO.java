package com.labotec.traccar.domain.web.dto.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverUpdateDTO {

    @NotNull(message = "First name is required")
    @Size(max = 250, message = "First name must not exceed 250 characters")
    @JsonProperty("first_name")
    private String firstName;

    @NotNull(message = "Document type is required")
    @JsonProperty("document_type")
    private Integer documentType;

    @NotNull(message = "Document number is required")
    @Size(max = 50, message = "Document number must not exceed 50 characters")
    @JsonProperty("document_number")
    private String documentNumber;

    @NotNull(message = "Status is required")
    @JsonProperty("status")
    private Byte status;

    @NotNull(message = "Company is required")
    @JsonProperty("company_id")
    private Integer companyId;  // Refers to the company id
}
