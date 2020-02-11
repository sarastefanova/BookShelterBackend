package com.example.books.model.exceptions;

public class UserAlreadyExists extends Exception {

   String message;
    public UserAlreadyExists(String message){
        super(message);
        this.message=message;
    }
}
