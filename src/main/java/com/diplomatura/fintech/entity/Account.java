package com.diplomatura.fintech.entity;

import com.diplomatura.fintech.enums.AccountStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 22)
    private String cbu;

    @Column(nullable = false, unique = true, length = 50)
    private String alias;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Account() {
    }

    public Account(String cbu, String alias, User user) {
        this.cbu = cbu;
        this.alias = alias;
        this.balance = BigDecimal.ZERO;
        this.status = AccountStatus.ACTIVE;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    // No utilizo el setId() porque la lógica la va a manejar la base de datos

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
