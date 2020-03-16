package com.example.books.repository;

import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.model.UserFavouriteBooks;
import com.example.books.model.UserFavouriteBooksKey;

import java.util.List;
import java.util.Optional;

public interface UserFavouriteBooksRepository {

    UserFavouriteBooks userFavouriteBookSave(UserFavouriteBooks userFavouriteBooks);
    List<Book> getAllFavouriteBooks();
    List<Book> getAllBooksFavouriteByUser(User user);
    Optional<UserFavouriteBooks> findById(UserFavouriteBooksKey userFavouriteBooksKey);
    int getStatusBookFavourite(User user,Book name);
    UserFavouriteBooks userFavouriteBookUpdate(UserFavouriteBooks userFavouriteBooks);
    void deleteFavouriteBook(User user, Book book);
    List<User> getAllUsersFavouriteBooks();
    List<UserFavouriteBooks> listFavoriteBooksUser(User user);
    UserFavouriteBooks findFavBookUser(User user, Book book);
}
