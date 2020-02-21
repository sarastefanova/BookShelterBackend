package com.example.books.repository;

import com.example.books.model.Book;
import com.example.books.model.paginate.Page;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> getAllBooks();
    Optional<Book> findById(String name);
    Page<Book> getAllBooks(int page, int size);
    Book save(Book book);
    void deleteById(String name);
    List<Book>getAllBookByAuthor(String nameAndSurname);
    Book checkIfBookExists(String name);

    List<Book> searchBookOrAuthor(String name);
    Long findAnotherSameUserName(String userName);
}
