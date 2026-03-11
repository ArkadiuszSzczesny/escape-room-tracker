package com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception;

public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }
}
