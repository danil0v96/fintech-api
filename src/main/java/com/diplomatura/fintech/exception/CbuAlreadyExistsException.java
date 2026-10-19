package com.diplomatura.fintech.exception;

public class CbuAlreadyExistsException extends RuntimeException {
    public CbuAlreadyExistsException(String cbu) {
        super("Ya existe una cuenta con el CBU: " + cbu);
    }
}
