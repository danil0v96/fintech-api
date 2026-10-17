package com.diplomatura.fintech.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long id) {
        super("No existe una cuenta con el id " + id);
    }
}
