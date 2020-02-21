package com.example.books.repository.jpa;

import com.example.books.model.Author;
import com.example.books.repository.AuthorRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    private final AuthorJpaRepository authorJpaRepository;

    public AuthorRepositoryImpl(AuthorJpaRepository authorJpaRepository) {
        this.authorJpaRepository = authorJpaRepository;
    }

    @Override
    public Author save(Author author) {
        return this.authorJpaRepository.save(author);
    }

    @Override
    public void deleteAuthor(String nameAndSurname) {
        this.authorJpaRepository.deleteById(nameAndSurname);
    }

    @Override
    public Optional<Author> findById(String nameAndSurname) {
        return this.authorJpaRepository.findById(nameAndSurname);
    }

    @Override
    public List<Author> getAllAuthors() {
        return this.authorJpaRepository.findAll();
    }

    @Override
    public List<String> getAllNamesOfAuthors() {
        return this.authorJpaRepository.getAllNamesOfAuthors();
    }

    @Override
    public Author checkArturName(String authorName) {
        return this.authorJpaRepository.checkArturName(authorName);
    }

    @Override
    public Long findAnotherSameAuthor(String userName) {
        return this.authorJpaRepository.findAnotherSameAuthor(userName);
    }


}
