package com.example.books.service;

import com.example.books.model.Book;
import com.example.books.model.exceptions.InvalidAuthorsName;
import com.example.books.model.exceptions.InvalidBookId;
import com.example.books.model.paginate.Page;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> listBooks();
    Optional<Book> getById(String name);
    Book createBook(String name, String nameAndSurname, int price) throws InvalidAuthorsName;
    Book createBookWithImg(String name, String nameAndSurname, int price,byte[] file,String shortContentBook) throws InvalidAuthorsName, IOException;
    void deleteBook(String name);
    Page<Book> getAllBooks(int page, int size);
    Book editBook(String name, String nameAndSurname, int price,String shortContentBook) throws InvalidBookId, InvalidAuthorsName;
    List<Book>getAllBookByAuthor(String nameAndSurname);
    List<Book> getAllBooksAuthor();
    List<Book> searchBookOrAuthor(String name);


}
