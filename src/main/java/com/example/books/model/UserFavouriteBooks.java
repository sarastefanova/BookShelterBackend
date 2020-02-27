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
public class UserFavouriteBooks {
    @EmbeddedId
    UserFavouriteBooksKey id;

    @ManyToOne
    @MapsId("user_favourite_book_id")
    @JoinColumn(name = "user_favourite_book_id")
    User user;

    @ManyToOne
    @MapsId("book_favourite_name")
    @JoinColumn(name = "book_favourite_name")
    Book book;

    int isOrdered;
}
