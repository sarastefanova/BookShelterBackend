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
    public Optional<Book> getById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Book createBook(String name, String nameAndSurname, int price) throws InvalidAuthorsName{
        Book book=this.bookRepository.checkIfBookExists(name);
        if(book==null){
            Author author=this.authorRepository.findById(nameAndSurname).orElseThrow(InvalidAuthorsId::new);

                book=new Book(name,author,price);



                return this.bookRepository.save(book);
        }
        try {
            throw new BookAlreadyExists("Book with this name already exists");
        } catch (BookAlreadyExists bookAlreadyExists) {
            bookAlreadyExists.getMessage();
        }
        return null;

    }

    @Override
    public Book createBookWithImg(String name, String nameAndSurname, int price, byte[] file) throws InvalidAuthorsName, IOException {
        Book book=this.bookRepository.checkIfBookExists(name);
        if(book==null){
            Author author=this.authorRepository.findById(nameAndSurname).orElseThrow(InvalidAuthorsId::new);

                book=new Book(name,author,price,file);
                return this.bookRepository.save(book);

        }
        try {
            throw new BookAlreadyExists("Book with this name already exists");
        } catch (BookAlreadyExists bookAlreadyExists) {
            bookAlreadyExists.getMessage();
        }
        return null;

    }

    @Override
    public void deleteBook(Long id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public Book editBook(Long id, String name, String nameAndSurname, int price) throws InvalidBookId, InvalidAuthorsName {
        Book updateBook=this.bookRepository.findById(id).orElseThrow(InvalidBookId::new);
        Author author=this.authorRepository.findById(nameAndSurname).orElseThrow(InvalidAuthorsId::new);

            updateBook.setName(name);
            updateBook.setAuthor(author);
            updateBook.setPrice(price);

            return this.bookRepository.save(updateBook);

    }

}
