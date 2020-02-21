package com.example.books.controller;

import com.example.books.model.Author;
import com.example.books.model.exceptions.AuthorAlreadyExists;
import com.example.books.model.exceptions.InvalidAuthorsId;
import com.example.books.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/author",produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class RestAuthorController {

    private final AuthorService authorService;

    public RestAuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author createAuthor(@RequestParam(value="nameAndSurname") String nameAndSurname,
                               @RequestParam(value="shortAuthorBiography") String shortAuthorBiography) {

        try {
            return this.authorService.createAuthor(nameAndSurname,shortAuthorBiography);
        } catch (AuthorAlreadyExists authorAlreadyExists) {
            authorAlreadyExists.getMessage();
        }
        return null;
    }


    @PostMapping(path = "/addAuthor")
    @ResponseStatus(HttpStatus.CREATED)
    public Author createAuthor(@RequestParam(value="nameAndSurname") String nameAndSurname,
                               @RequestParam(value="shortAuthorBiography") String shortAuthorBiography,
                               @RequestParam(value = "file",required = false) MultipartFile file) throws IOException, AuthorAlreadyExists {


            return this.authorService.createAuthorImg(nameAndSurname,shortAuthorBiography,file.getBytes());

    }

    @PatchMapping("/{nameAndSurname}")
    public Author updateAuthor(@PathVariable String nameAndSurname,
                               @RequestParam(value="shortAuthorBiography")String shortAuthorBiography) throws InvalidAuthorsId {
       return this.authorService.editAuthor(nameAndSurname,shortAuthorBiography);
    }

    @DeleteMapping("/{nameAndSurname}")
    public void deleteAuthor(@PathVariable String nameAndSurname){
        this.authorService.deleteAuthor(nameAndSurname);
    }

    @GetMapping
    public List<Author> getAllAuthors(){
        return this.authorService.listAuthors();
    }


    @GetMapping(params = "nameAndSurname")
    public Optional<Author> searchByName(@RequestParam String nameAndSurname){
        return authorService.getById(nameAndSurname);
    }

    @GetMapping(path = "/allNames")
    public List<String> getAllAuthorsNames(){
        return this.authorService.listAllNamesOfAuthors();
    }

}
