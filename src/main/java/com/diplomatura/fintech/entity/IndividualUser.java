package com.diplomatura.fintech.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("INDIVIDUAL")
public class IndividualUser extends User {

    private String name;
    private String lastName;
    private String document;

    public IndividualUser() {
        super();
    }

    public IndividualUser(String name, String lastName, String email, String document) {
        super(email);
        this.name = name;
        this.lastName = lastName;
        this.document = document;
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

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
