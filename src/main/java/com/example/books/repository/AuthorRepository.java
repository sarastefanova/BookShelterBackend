package com.example.books.repository;

import com.example.books.model.Author;
import com.example.books.model.paginate.Page;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Author save(Author author);
    void deleteAuthor(String nameAndSurname);
    Optional<Author> findById(String nameAndSurname);
    List<Author> getAllAuthors();

    List<String> getAllNamesOfAuthors();
    Author checkArturName(String authorName);
    Long findAnotherSameAuthor(String userName);

    List<Author> getAllAuthorsFlag();

    String getAuthorName(String nameAndSurname);

    Page<Author> getAllAuthorsPaginate(int page, int size);
}
