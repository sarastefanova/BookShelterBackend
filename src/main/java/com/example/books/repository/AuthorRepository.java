package com.example.books.repository;

import com.example.books.model.Author;
import com.example.books.model.paginate.Page;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Author save(Author author);

    Optional<Author> findById(String nameAndSurname);


    List<String> getAllNamesOfAuthors();

    Long findAnotherSameAuthor(String userName);



    String getAuthorName(String nameAndSurname);

    Page<Author> getAllAuthorsPaginate(int page, int size);
}
