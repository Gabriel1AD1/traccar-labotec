package com.labotec.traccar.infra.web.controller.rest.traccar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserCreateNotAdministratorDTO {
    @NotEmpty
    @JsonProperty("username")
    private String username;
    @NotEmpty
    @JsonProperty("email")
    @Email
    private String email;
    @NotEmpty
    @JsonProperty("password")
    private String password;
}
