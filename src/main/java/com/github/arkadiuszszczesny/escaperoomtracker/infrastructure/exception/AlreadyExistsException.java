package com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String message){
        super(message);
    }
}
