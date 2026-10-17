package com.diplomatura.fintech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateAccountRequest {

    @NotBlank(message = "El CBU es obligatorio")
    @Pattern(
            regexp = "\\d{22}",
            message = "El CBU debe contener exactamente 22 dígitos"
    )
    private String cbu;

    @NotBlank(message = "El alias es obligatorio")
    @Size(
            max = 50,
            message = "El alias no puede superar los 50 caracteres"
    )
    private String alias;

    @NotNull(message = "El userId es obligatorio")
    @Positive(message = "El userId debe ser mayor que cero")
    private Long userId;

    public CreateAccountRequest() {
    }

    public CreateAccountRequest(String cbu, String alias, Long userId) {
        this.cbu = cbu;
        this.alias = alias;
        this.userId = userId;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
