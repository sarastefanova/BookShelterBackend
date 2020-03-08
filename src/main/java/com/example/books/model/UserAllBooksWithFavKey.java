package com.example.books.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserAllBooksWithFavKey implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "book_name")
    String bookName;

    public int hashCode() {
        return (int)(userId + Integer.parseInt(bookName));
    }

    public boolean equals(Object object) {
        return true;
    }

    public UserAllBooksWithFavKey(Long userId, String bookName) {
        this.userId = userId;
        this.bookName = bookName;
    }

    public UserAllBooksWithFavKey(){}
}
