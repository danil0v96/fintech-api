package com.diplomatura.fintech.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("No existe un usuario con el id: " + id);
    }
}
