package com.example.books.repository.jpa;

import com.example.books.model.Book;
import com.example.books.model.paginate.Page;
import com.example.books.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final BookJpaRepository bookJpaRepository;

    public BookRepositoryImpl(BookJpaRepository bookJpaRepository) {
        this.bookJpaRepository = bookJpaRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return this.bookJpaRepository.findAll();
    }

    @Override
    public Optional<Book> findById(String name) {
        return this.bookJpaRepository.findById(name);
    }

    @Override
    public Page<Book> getAllBooks(int page, int size) {
        org.springframework.data.domain.Page<Book> result=this.bookJpaRepository.findAll(PageRequest.of(page, size));
        //Page<Book>newBook=this.bookJpaRepository.findAll(PageRequest.of(page,size)).map(item->)
        List<Book>newBooks=this.bookJpaRepository.findAllAuthors();
        int totalPages=Math.round((float) newBooks.size()/size);
        //return new  Page<>(page,newBooks.size(),size,newBooks);

        return  Page.slice(newBooks,page,size);
    }

    @Override
    public Book save(Book book) {
        return this.bookJpaRepository.save(book);
    }

    @Override
    public void deleteById(String name) {
            this.bookJpaRepository.deleteById(name);
    }

    @Override
    public List<Book> getAllBookByAuthor(String nameAndSurname) {
        return this.bookJpaRepository.getAllBookByAuthor(nameAndSurname);
    }

    @Override
    public Book checkIfBookExists(String name) {
        return this.bookJpaRepository.checkIfBookExists(name);
    }

    @Override
    public List<Book> searchBookOrAuthor(String name) {
        return this.bookJpaRepository.findByName(name);
    }

    @Override
    public Long findAnotherSameUserName(String userName) {
        return this.bookJpaRepository.findAnotherSameUserName(userName);
    }

    @Override
    public List<Book> getAllBooksAuthor() {

         List<Book> booksWithoutDeletedAuthors=this.bookJpaRepository.findAllAuthors();

        return null;
    }
}
