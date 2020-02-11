package com.example.books.repository;

import com.example.books.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> getAllBooks();
    Optional<Book> findById(Long id);
    Book save(Book book);
    void deleteById(Long id);

    Book checkIfBookExists(String name);
}
