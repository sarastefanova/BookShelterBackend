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
public class userOrdered {

    @EmbeddedId
    userOrderedBooksKey id;

    @ManyToOne
    @MapsId("all_ordered_books_user")
    @JoinColumn(name = "all_ordered_books_user")
    User user;

    @ManyToOne
    @MapsId("ordered_books")
    @JoinColumn(name = "ordered_books")
    Book book;

    int status;
}
