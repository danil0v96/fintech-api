package com.diplomatura.fintech.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateLegalEntityUserRequest {

    @NotBlank(message = "La razón social es obligatoria")
    @Size(max = 120, message = "La razón social no puede superar los 120 caracteres")
    private String businessName;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Size(max = 150, message = "El email no puede superar los 150 caracteres")
    private String email;

    @NotBlank(message = "El CUIT es obligatorio")
    @Pattern(regexp = "\\d{11}", message = "El CUIT debe contener 11 dígitos")
    private String cuit;

    public CreateLegalEntityUserRequest() {
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }
}
