package com.example.books.repository;

import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.model.userOrdered;
import com.example.books.model.userOrderedBooksKey;

import java.util.List;
import java.util.Optional;

public interface UserOrderedBooks{

    userOrdered userOrderedSave(userOrdered userOrdered);
    List<Book> getAllOrders();
    List<Book> getAllBooksOrderedUser(User user);
    Optional<userOrdered>findById(userOrderedBooksKey userOrderedKey);
    int getStatusBookOrdered(User user,Book name);

    void deleteOrder(User user, Book book);

    User getUserByBook(User user,Book book);

    userOrdered findUserOrder(User user, Book book);

    List<userOrdered> getAllRequests();
}
