package com.demo.demo.Exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message); //call constructor and pass in message
    }
}
