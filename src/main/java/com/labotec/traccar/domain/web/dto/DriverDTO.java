package com.labotec.traccar.domain.web.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverDTO {

    @NotNull(message = "First name is required")
    @Size(max = 250, message = "First name must not exceed 250 characters")
    private String firstName;

    @NotNull(message = "Document type is required")
    private Integer documentType;

    @NotNull(message = "Document number is required")
    @Size(max = 50, message = "Document number must not exceed 50 characters")
    private String documentNumber;

    @NotNull(message = "Status is required")
    private Byte status;

    @NotNull(message = "Company is required")
    private Integer companyId;  // Refers to the company id
}
