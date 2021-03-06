package com.example.books.service;

import com.example.books.model.*;
import com.example.books.model.exceptions.InvalidAuthorsName;
import com.example.books.model.exceptions.InvalidBookId;
import com.example.books.model.paginate.Page;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> getById(String name);
    Book createBookWithImg(String name, String nameAndSurname, int price,byte[] file,String shortContentBook,int availability) throws InvalidAuthorsName, IOException;
    void deleteBook(String name);
    Page<Book> getAllBooks(int page, int size,Long userId);
    Book editBook(String name, String nameAndSurname, int price,String shortContentBook,int availability) throws InvalidBookId, InvalidAuthorsName;
    List<Book>getAllBookByAuthor(String nameAndSurname);
    Author getAuthorByBook(String name);
    Page<UserAllBooksWithFav> searchBookPage(String name,int page, int pageSize, Long id);
    List<Book> getNewestBooks();
    Page<UserAllBooksWithFav> getAllBooksUserWithFav(int page, int size, Long id);
}
