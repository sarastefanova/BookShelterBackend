package com.example.books.model;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    String name;

    @ManyToOne
    Author author;
    int price;

    @Column(nullable = true)
    @Lob
    private byte[] file;

    String shortContentBook;
    public Book(String name, Author author, int price) {
        this.name = name;
        this.author = author;
        this.price = price;

    }

    public Book(String name, Author author, int price, byte[] file) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.file = file;
    }
}
