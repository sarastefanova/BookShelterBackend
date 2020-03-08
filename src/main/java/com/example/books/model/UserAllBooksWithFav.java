package com.example.books.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAllBooksWithFav {

    @EmbeddedId
    UserAllBooksWithFavKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("book_name")
    @JoinColumn(name = "book_name")
    Book book;


    int inFavourite=0;
}
