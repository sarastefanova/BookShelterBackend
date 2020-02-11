package com.example.books.model.exceptions;

public class BookAlreadyExists extends Exception {

    public String s;
    public BookAlreadyExists(String s) {
        super(s);
        this.s=s;
    }
}
