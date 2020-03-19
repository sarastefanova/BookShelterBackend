package com.example.books.controller;

import com.example.books.model.*;
import com.example.books.model.exceptions.InvalidAuthorsId;
import com.example.books.model.exceptions.InvalidAuthorsName;
import com.example.books.model.exceptions.InvalidBookId;
import com.example.books.model.exceptions.InvalidUserId;
import com.example.books.model.paginate.Page;
import com.example.books.service.BookService;
import com.example.books.service.UserService;
import org.springframework.http.HttpStatus;
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
    private final UserService userService;
    public RestBookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @PostMapping(path = "/uploadBook")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBookImg(@RequestParam(value="name") String name,
                           @RequestParam(value="nameAndSurname") String nameAndSurname,
                           @RequestParam(value="price") String price,
                           @RequestParam(value = "file",required = false)MultipartFile file,
                              @RequestParam(value = "shortContentBook")String shortContentBook,
                              @RequestParam(value = "availability")int availability) throws InvalidAuthorsName, IOException {
        int priceBook=Integer.parseInt(price);
        return this.bookService.createBookWithImg(name,nameAndSurname,priceBook,file.getBytes(),shortContentBook,availability);
    }

    @GetMapping
    public Page<Book> getAllBooks(@RequestHeader(name = "page", defaultValue = "0", required = false) int page,
                                              @RequestHeader(name = "page-size", defaultValue = "10", required = false) int size,
                                  @RequestParam(value = "id",required = false,defaultValue = "null")Long id) {
        return this.bookService.getAllBooks(page, size,id);
    }

    @GetMapping(path = "/getAllBooksUser")
    public Page<UserAllBooksWithFav> getAllBooksUserWithFav(@RequestHeader(name = "page", defaultValue = "0", required = false) int page,
                                                            @RequestHeader(name = "page-size", defaultValue = "6", required = false) int size,
                                                            @RequestParam(value = "id")Long id){
        return this.bookService.getAllBooksUserWithFav(page,size,id);
    }

    @PatchMapping("/{name}")
    public Book updateBook(
                           @PathVariable(value="name") String name,
                           @RequestParam(value="nameAndSurname") String nameAndSurname,
                           @RequestParam(value="price") int price,
                           @RequestParam(value = "shortContentBook")String shortContentBook,
                           @RequestParam(value = "availability")int availability) throws InvalidAuthorsId, InvalidBookId, InvalidAuthorsName, IOException {
        return this.bookService.editBook(name,nameAndSurname,price,shortContentBook,availability);
    }

    @DeleteMapping("/{name}")
    public void deleteBook(@PathVariable String name){
        this.bookService.deleteBook(name);
    }

    @GetMapping(path = "/getNewestBooks")
    public List<Book>getNewestBooks(){
        return this.bookService.getNewestBooks();
    }

    @GetMapping(params = "name")
    public Optional<Book> searchByName(@RequestParam String name){
        return bookService.getById(name);
    }

    @GetMapping(path = "/searchBookPage")
    public Page<UserAllBooksWithFav> searchBookPage(@RequestParam(value = "name") String name,
                                                    @RequestParam(value = "id", defaultValue = "3") Long id,
                                                    @RequestHeader(name = "page", defaultValue = "0", required = false) int page,
                                                    @RequestHeader(name = "page-size", defaultValue = "6", required = false) int size){
        return bookService.searchBookPage(name,page,size,id);
    }

    @GetMapping(path = "/getAllBooksByAuthor/{nameAndSurname}")
    public List<Book>getAllBookByAuthor(@PathVariable String nameAndSurname){
        return this.bookService.getAllBookByAuthor(nameAndSurname);
    }

    @GetMapping("/{name}/authorBook")
    public Author getAuthorByBook(@PathVariable String name){
        return this.bookService.getAuthorByBook(name);
    }
}
