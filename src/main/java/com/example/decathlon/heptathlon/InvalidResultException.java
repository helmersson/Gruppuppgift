package com.example.decathlon.heptathlon;

//A new Exception
public class InvalidResultException extends Exception {
    public InvalidResultException(String message) {
        super(message);
    }
}