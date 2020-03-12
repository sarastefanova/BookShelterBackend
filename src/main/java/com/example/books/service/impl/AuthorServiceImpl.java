package com.example.books.service.impl;

import com.example.books.model.Author;

import com.example.books.model.exceptions.InvalidAuthorsId;
import com.example.books.model.paginate.Page;
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
    public Optional<Author> getById(String nameAndSurname) {
        return this.authorRepository.findById(nameAndSurname);
    }

    @Override
    public List<Author> listAuthors() {
        return this.authorRepository.getAllAuthorsFlag();
    }



    @Override
    public Author createAuthorImg(String nameAndSurname, String shortAuthorBiography, byte[] file)  {
        if((this.authorRepository.findAnotherSameAuthor(nameAndSurname))==null) {
            int isDeleted=0;
            Author author = new Author(nameAndSurname, shortAuthorBiography, file,isDeleted);
            return this.authorRepository.save(author);
        }
        else throw new InvalidAuthorsId();
    }



    @Override
    public void deleteAuthorWithFlag(String nameAndSurname,int isDeleted) {
        Author newAuthor=this.authorRepository.findById(nameAndSurname).orElseThrow(InvalidAuthorsId::new);
            newAuthor.setIsDeleted(isDeleted);
            this.authorRepository.save(newAuthor);

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

    @Override
    public String getAuthorName(Author author) {
        return this.authorRepository.getAuthorName(author.getNameAndSurname());
    }

    @Override
    public Page<Author> getAllAuthorsPaginate(int page, int size) {
        return this.authorRepository.getAllAuthorsPaginate(page,size);
    }
}
