package com.example.books.controller;

import com.example.books.model.Author;
import com.example.books.model.exceptions.AuthorAlreadyExists;
import com.example.books.model.exceptions.InvalidAuthorsId;
import com.example.books.model.paginate.Page;
import com.example.books.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping
    public List<Author> getAllAuthors(){
        return this.authorService.listAuthors();
    }

    @PostMapping(path = "/addAuthor")
    @ResponseStatus(HttpStatus.CREATED)
    public Author createAuthor(@RequestParam(value="nameAndSurname") String nameAndSurname,
                               @RequestParam(value="shortAuthorBiography") String shortAuthorBiography,
                               @RequestParam(value = "file",required = false) MultipartFile file) throws IOException, AuthorAlreadyExists {
            return this.authorService.createAuthor(nameAndSurname,shortAuthorBiography,file.getBytes());
    }

    @PatchMapping("/{nameAndSurname}")
    public Author updateAuthor(@PathVariable String nameAndSurname,
                               @RequestParam(value="shortAuthorBiography")String shortAuthorBiography) throws InvalidAuthorsId {
       return this.authorService.editAuthor(nameAndSurname,shortAuthorBiography);
    }

    @DeleteMapping(path = "/delete/{nameAndSurname}")
    public void deleteAuthor(@PathVariable String nameAndSurname,@RequestParam(value = "isDeleted") int isDeleted){
        this.authorService.deleteAuthor(nameAndSurname,isDeleted);
    }

    @GetMapping(path = "/allAuthorsPaginate")
    public Page<Author> getAllAuthors(@RequestHeader(name = "page", defaultValue = "0", required = false) int page,
                                      @RequestHeader(name = "page-size", defaultValue = "10", required = false) int size) {
        return this.authorService.getAllAuthorsPaginate(page, size);
    }

    @GetMapping(params = "nameAndSurname")
    public Optional<Author> searchByName(@RequestParam String nameAndSurname){
        return authorService.getById(nameAndSurname);
    }

    @GetMapping("/getAuthorName")
    public String getAuthorsName(@RequestBody Author author){
        return this.authorService.getAuthorName(author);
    }

    @GetMapping(path = "/allNames")
    public List<String> getAllAuthorsNames(){
        return this.authorService.listAllNamesOfAuthors();
    }

}
