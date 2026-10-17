package com.diplomatura.fintech.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("LEGAL_ENTITY")
public class LegalEntityUser extends User {

    private String businessName;
    private String cuit;

    public LegalEntityUser() {
        super();
    }

    public LegalEntityUser(String businessName, String email, String cuit) {
        super(email);
        this.businessName = businessName;
        this.cuit = cuit;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }
}
