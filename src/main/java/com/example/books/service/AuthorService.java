package com.example.books.service;

import com.example.books.model.Author;
import com.example.books.model.exceptions.AuthorAlreadyExists;
import com.example.books.model.exceptions.InvalidAuthorsId;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> listAuthors();
    Optional<Author> getById(String nameAndSurname);
    Author createAuthor(String nameAndSurname, String shortAuthorBiography) throws AuthorAlreadyExists;
    void deleteAuthor(String nameAndSurname);
    List<String> listAllNamesOfAuthors();
    Author editAuthor(String nameAndSurname, String shortAuthorBiography) throws InvalidAuthorsId;

}
