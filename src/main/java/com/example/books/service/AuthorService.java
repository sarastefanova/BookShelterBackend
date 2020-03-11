package com.example.books.service;

import com.example.books.model.Author;
import com.example.books.model.exceptions.AuthorAlreadyExists;
import com.example.books.model.exceptions.InvalidAuthorsId;
import com.example.books.model.paginate.Page;

import java.util.List;
import java.util.Optional;

public interface AuthorService {


    Optional<Author> getById(String nameAndSurname);

    Author createAuthorImg(String nameAndSurname, String shortAuthorBiography,byte[]file) throws AuthorAlreadyExists;

    void deleteAuthorWithFlag(String nameAndSurname,int isDeleted);
    List<String> listAllNamesOfAuthors();
    Author editAuthor(String nameAndSurname, String shortAuthorBiography) throws InvalidAuthorsId;

    String getAuthorName(Author author);

    Page<Author> getAllAuthorsPaginate(int page, int size);
}
