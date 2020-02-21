package com.example.books.service.impl;

import com.example.books.model.Author;

import com.example.books.model.exceptions.InvalidAuthorsId;
import com.example.books.repository.AuthorRepository;
import com.example.books.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> listAuthors() {
        return this.authorRepository.getAllAuthors();
    }

    @Override
    public Optional<Author> getById(String nameAndSurname) {
        return this.authorRepository.findById(nameAndSurname);
    }

    @Override
    public Author createAuthor(String nameAndSurname, String shortAuthorBiography){

        if((this.authorRepository.findAnotherSameAuthor(nameAndSurname))==null){
            Author  author=new Author(nameAndSurname,shortAuthorBiography);
            return this.authorRepository.save(author);
        }
        else throw new InvalidAuthorsId();

    }

    @Override
    public Author createAuthorImg(String nameAndSurname, String shortAuthorBiography, byte[] file)  {
        if((this.authorRepository.findAnotherSameAuthor(nameAndSurname))==null) {
            Author author = new Author(nameAndSurname, shortAuthorBiography, file);
            return this.authorRepository.save(author);
        }
        else throw new InvalidAuthorsId();
    }

    @Override
    public void deleteAuthor(String nameAndSurname) {
            this.authorRepository.deleteAuthor(nameAndSurname);
    }

    @Override
    public List<String> listAllNamesOfAuthors() {
        return this.authorRepository.getAllNamesOfAuthors();
    }

    @Override
    public Author editAuthor(String nameAndSurname, String shortAuthorBiography) throws InvalidAuthorsId {
        Author newAuthor=this.authorRepository.findById(nameAndSurname).orElseThrow(InvalidAuthorsId::new);
        newAuthor.setShortAuthorBiography(shortAuthorBiography);

      return   this.authorRepository.save(newAuthor);
    }
}
