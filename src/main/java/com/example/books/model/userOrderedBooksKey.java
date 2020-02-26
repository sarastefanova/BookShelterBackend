package com.example.books.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class userOrderedBooksKey implements Serializable {
    @Column(name = "all_ordered_books_user")
    Long userId;

    @Column(name = "ordered_books")
    String bookName;

    public int hashCode() {
        return (int)(userId + Integer.parseInt(bookName));
    }

    public boolean equals(Object object) {
        return true;
    }

    public userOrderedBooksKey(Long userId, String bookName) {
        this.userId = userId;
        this.bookName = bookName;
    }

    public userOrderedBooksKey(){}
}
