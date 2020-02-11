package com.example.books.repository;

import com.example.books.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Author save(Author author);
    void deleteAuthor(String nameAndSurname);
    Optional<Author> findById(String nameAndSurname);
    List<Author> getAllAuthors();

    List<String> getAllNamesOfAuthors();
    Author checkArturName(String authorName);
}
