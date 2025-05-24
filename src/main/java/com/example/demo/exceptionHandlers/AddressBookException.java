package com.example.demo.exceptionHandlers;



public class AddressBookException extends RuntimeException {
    public AddressBookException(String message) {
        super(message);
    }
}
