package com.example.books.repository.jpa;

import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.model.UserFavouriteBooks;
import com.example.books.model.UserFavouriteBooksKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserFavouriteBooksRepositoryJpa extends JpaRepository<UserFavouriteBooks, UserFavouriteBooksKey> {

    @Query("select b.book from UserFavouriteBooks b")
    List<Book> getAllBooksFavourite();

    @Query("select b.book from UserFavouriteBooks b where b.user like :user")
    List<Book> getBooksFavouriteByUser(User user);

    @Query("select b.isOrdered from UserFavouriteBooks b where b.book like :name and b.user like :user")
    int getStatusBookFavourite(User user,Book name);

    @Query("select b from UserFavouriteBooks b where b.book like :book and b.user like :user")
    UserFavouriteBooks findFavBookUser(User user, Book book);

    @Query("select u from UserFavouriteBooks u where u.book like :book and u.user like :user")
    UserFavouriteBooks findByIdBook(User user, Book book);
}
