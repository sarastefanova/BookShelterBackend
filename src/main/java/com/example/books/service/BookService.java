package com.example.books.service;

import com.example.books.model.Author;
import com.example.books.model.Book;
import com.example.books.model.exceptions.InvalidAuthorsName;
import com.example.books.model.exceptions.InvalidBookId;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> listBooks();
    Optional<Book> getById(Long id);
    Book createBook(String name, String nameAndSurname, int price) throws InvalidAuthorsName;
    Book createBookWithImg(String name, String nameAndSurname, int price,byte[] file) throws InvalidAuthorsName, IOException;
    void deleteBook(Long id);

    Book editBook(Long id,String name, String nameAndSurname, int price) throws InvalidBookId, InvalidAuthorsName;
}
