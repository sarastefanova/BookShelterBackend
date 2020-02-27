package com.example.books.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Embeddable
public class UserFavouriteBooksKey  implements Serializable {

//    @Column(name = "user_book_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long Id;

    @Column(name = "user_favourite_book_id")
    Long userId;

    @Column(name = "book_favourite_name")
    String bookName;

    public int hashCode() {
        return (int)(userId + Integer.parseInt(bookName));
    }

    public boolean equals(Object object) {
        return true;
    }

    public UserFavouriteBooksKey(Long userId, String bookName) {
        this.userId = userId;
        this.bookName = bookName;
    }

    public UserFavouriteBooksKey(){}
}
