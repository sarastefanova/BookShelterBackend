package com.example.books.service.impl;

import com.example.books.model.Author;
import com.example.books.model.Book;
import com.example.books.model.exceptions.BookAlreadyExists;
import com.example.books.model.exceptions.InvalidAuthorsId;
import com.example.books.model.exceptions.InvalidAuthorsName;
import com.example.books.model.exceptions.InvalidBookId;
import com.example.books.repository.AuthorRepository;
import com.example.books.repository.BookRepository;
import com.example.books.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> listBooks() {
        return this.bookRepository.getAllBooks();
    }

    @Override
    public Optional<Book> getById(String name) {
        return this.bookRepository.findById(name);
    }

    @Override
    public Book createBook(String name, String nameAndSurname, int price) throws InvalidAuthorsName{


            Author author=this.authorRepository.findById(nameAndSurname).orElseThrow(InvalidAuthorsId::new);

               Book book=new Book(name,author,price);

                return this.bookRepository.save(book);
    }

    @Override
    public Book createBookWithImg(String name, String nameAndSurname, int price, byte[] file) throws InvalidAuthorsName, IOException {

            Author author=this.authorRepository.findById(nameAndSurname).orElseThrow(InvalidAuthorsId::new);

              Book   book=new Book(name,author,price,file);
                return this.bookRepository.save(book);
    }

    @Override
    public void deleteBook(String name) {
        this.bookRepository.deleteById(name);
    }

    @Override
    public Book editBook(String name, String nameAndSurname, int price) throws InvalidBookId, InvalidAuthorsName {
        Book updateBook=this.bookRepository.findById(name).orElseThrow(InvalidBookId::new);
        Author author=this.authorRepository.findById(nameAndSurname).orElseThrow(InvalidAuthorsId::new);

            updateBook.setAuthor(author);
            updateBook.setPrice(price);

            return this.bookRepository.save(updateBook);

    }

}
