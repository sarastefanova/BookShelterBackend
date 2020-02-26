package com.example.books.repository;

import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.model.userOrdered;

import java.util.List;

public interface UserOrderedBooks{

    userOrdered userOrderedSave(userOrdered userOrdered);
    List<Book> getAllOrders();
    List<Book> getAllBooksOrderedUser(User user);

    int getStatusBookOrdered(Book name);
}
