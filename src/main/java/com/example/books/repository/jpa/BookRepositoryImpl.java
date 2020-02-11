package com.example.books.repository.jpa;

import com.example.books.model.Book;
import com.example.books.repository.BookRepository;
import org.springframework.stereotype.Repository;

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
    public Optional<Book> findById(Long id) {
        return this.bookJpaRepository.findById(id);
    }

    @Override
    public Book save(Book book) {
        return this.bookJpaRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {
            this.bookJpaRepository.deleteById(id);
    }

    @Override
    public Book checkIfBookExists(String name) {
        return this.bookJpaRepository.checkIfBookExists(name);
    }
}
