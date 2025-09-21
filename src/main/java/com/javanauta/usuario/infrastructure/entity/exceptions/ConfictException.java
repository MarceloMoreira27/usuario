package com.javanauta.usuario.infrastructure.entity.exceptions;

public class ConfictException  extends RuntimeException{

    public ConfictException(String message) {
        super(message);
    }

    public ConfictException(String message, Throwable cause) {
        super(message, cause);
    }
}
