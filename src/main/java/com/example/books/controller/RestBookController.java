package com.example.books.controller;

import com.example.books.model.Author;
import com.example.books.model.Book;
import com.example.books.model.exceptions.InvalidAuthorsId;
import com.example.books.model.exceptions.InvalidAuthorsName;
import com.example.books.model.exceptions.InvalidBookId;
import com.example.books.model.paginate.Page;
import com.example.books.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/books",produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class RestBookController {

    private final BookService bookService;

    public RestBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestParam(value="name") String name,
                             @RequestParam(value="nameAndSurname") String nameAndSurname,
                             @RequestParam(value="price") String price) throws InvalidAuthorsName, IOException {


            int priceBook=Integer.parseInt(price);
            return this.bookService.createBook(name,nameAndSurname,priceBook);


    }


    @PostMapping(path = "/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBookImg(@RequestParam(value="name") String name,
                           @RequestParam(value="nameAndSurname") String nameAndSurname,
                           @RequestParam(value="price") String price,
                           @RequestParam(value = "file",required = false)MultipartFile file) throws InvalidAuthorsName, IOException {


        int priceBook=Integer.parseInt(price);

            return this.bookService.createBookWithImg(name,nameAndSurname,priceBook,file.getBytes());
    }


    @GetMapping
    public Page<Book> getAllBooks(@RequestHeader(name = "page", defaultValue = "0", required = false) int page,
                                              @RequestHeader(name = "page-size", defaultValue = "10", required = false) int size) {
        return this.bookService.getAllBooks(page, size);
    }

    @PatchMapping("/{name}")
    public Book updateBook(
                           @PathVariable(value="name") String name,
                           @RequestParam(value="nameAndSurname") String nameAndSurname,
                           @RequestParam(value="price") int price) throws InvalidAuthorsId, InvalidBookId, InvalidAuthorsName {
        return this.bookService.editBook(name,nameAndSurname,price);
    }

    @DeleteMapping("/{name}")
    public void deleteAuthor(@PathVariable String name){
        this.bookService.deleteBook(name);
    }



    @GetMapping(params = "name")
    public Optional<Book> searchByName(@RequestParam String name){
        return bookService.getById(name);
    }

}
