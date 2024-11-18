package com.labotec.traccar.infra.web.controller.rest.traccar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserAdminCompanyDTO {

    @JsonProperty("secret_code")
    private String secretCode;

    @NotNull(message = "El nombre de la compañía no puede ser nulo")
    @NotEmpty(message = "El nombre de la compañía no puede estar vacío")
    @JsonProperty("company_name")
    private String companyName;

    @NotNull(message = "La dirección de la compañía no puede ser nula")
    @NotEmpty(message = "La dirección de la compañía no puede estar vacía")
    @JsonProperty("company_address")
    private String companyAddress;

    @NotNull(message = "El dominio de la compañía no puede ser nulo")
    @NotEmpty(message = "El dominio de la compañía no puede estar vacío")
    @JsonProperty("company_domain")
    private String companyDomain;

    @NotNull(message = "El correo electrónico de la compañía no puede ser nulo")
    @NotEmpty(message = "El correo electrónico de la compañía no puede estar vacío")
    @Email(message = "El formato del correo electrónico de la compañía es inválido")
    @JsonProperty("company_email")
    private String companyEmail;

    @NotNull(message = "El teléfono de la compañía no puede ser nulo")
    @NotEmpty(message = "El teléfono de la compañía no puede estar vacío")
    @JsonProperty("company_phone")
    private String companyPhone;

    // ADMIN DATA
    @NotNull(message = "El nombre de usuario del administrador no puede ser nulo")
    @NotEmpty(message = "El nombre de usuario del administrador no puede estar vacío")
    @JsonProperty("admin_user_name")
    private String adminUserName;

    @NotNull(message = "La contraseña del administrador no puede ser nula")
    @NotEmpty(message = "La contraseña del administrador no puede estar vacía")
    @Size(min = 6, message = "La contraseña del administrador debe tener al menos 6 caracteres")
    @JsonProperty("admin_password")
    private String adminPassword;

    @NotNull(message = "El correo electrónico del administrador no puede ser nulo")
    @NotEmpty(message = "El correo electrónico del administrador no puede estar vacío")
    @Email(message = "El formato del correo electrónico del administrador es inválido")
    @JsonProperty("admin_email")
    private String adminEmail;
}
