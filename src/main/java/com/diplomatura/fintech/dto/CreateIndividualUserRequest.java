package com.diplomatura.fintech.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateIndividualUserRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "El nombre no puede superar los 80 caracteres")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 80, message = "El apellido no puede superar los 80 caracteres")
    private String lastName;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Size(max = 150, message = "El email no puede superar los 150 caracteres")
    private String email;

    @NotBlank(message = "El documento es obligatorio")
    @Size(max = 20, message = "El documento no puede superar los 20 caracteres")
    private String document;

    public CreateIndividualUserRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
