package com.example.books.repository;

import com.example.books.model.*;
import com.example.books.model.paginate.Page;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Optional<Book> findById(String name);
    Page<Book> getAllBooks(int page, int size);
    Book save(Book book);
    void deleteById(String name);
    List<Book>getAllBookByAuthor(String nameAndSurname);
    List<Book> searchBookOrAuthor(String name);
    Long findAnotherSameUserName(String userName);
    Author getAuthorByBook(String name);
    Page<UserFavouriteBooks> getAllBooksAuthorFavourite(int page, int size, User user);
    Page<UserAllBooksWithFav> getAllBooksUserWithFav(int page, int size,Long id);
    List<Book> getNewestBooks();
}
