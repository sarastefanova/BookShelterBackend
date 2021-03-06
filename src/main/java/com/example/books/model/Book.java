package com.example.books.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @Lob
    String shortContentBook;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    int availability;



    int isDeleted;
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

    public Book(String name, Author author, int price, byte[] file, String shortContentBook,int availability) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.file = file;
        this.shortContentBook = shortContentBook;
        this.availability=availability;
    }
}
