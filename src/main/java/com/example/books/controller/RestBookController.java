package com.example.books.controller;

import com.example.books.model.Author;
import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.model.UserFavouriteBooks;
import com.example.books.model.exceptions.InvalidAuthorsId;
import com.example.books.model.exceptions.InvalidAuthorsName;
import com.example.books.model.exceptions.InvalidBookId;
import com.example.books.model.paginate.Page;
import com.example.books.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

//    @PreAuthorize("hasRole('admin')")
    @PostMapping(path = "/upload")
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
                                              @RequestHeader(name = "page-size", defaultValue = "10", required = false) int size) {
        return this.bookService.getAllBooks(page, size);
    }

    @GetMapping(path = "/getAllBooks")
    public List<Book> getAllBooksAuthor(@RequestHeader(name = "page", defaultValue = "0", required = false) int page,
                                  @RequestHeader(name = "page-size", defaultValue = "6", required = false) int size) {
        return this.bookService.getAllBooksAuthor();
    }

   // @PreAuthorize("hasRole('admin')")
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
    public void deleteAuthor(@PathVariable String name){
        this.bookService.deleteBook(name);
    }

    @GetMapping("/getAllBooksAuthorFavourite/{id}")
    public Page<UserFavouriteBooks>getAllBooksAuthorFavourite(@RequestHeader(name = "page", defaultValue = "0", required = false) int page,
                                                              @RequestHeader(name = "page-size", defaultValue = "10", required = false) int size,
                                                              @PathVariable(value = "id")Long id){
        return this.bookService.getAllBooksAuthorFavourite(page,size,id);
    }

    @GetMapping(path = "/getNewestBooks")
    public List<Book>getNewestBooks(){
        return this.bookService.getNewestBooks();
    }

    @GetMapping("/checkIfUserHasThisBookFav/{id}/{name}")
    public boolean checkIfUserHasThisBookFav( @PathVariable(value = "id")Long id,
                                       @PathVariable(value = "name")String name){

        return this.bookService.checkIfUserHasThisBookFav(id,name);
    }

    @GetMapping(params = "name")
    public Optional<Book> searchByName(@RequestParam String name){
        return bookService.getById(name);
    }

    @GetMapping(path = "/searchBook",params = "name")
    public List<Book> searchBookOrAuthor(@RequestParam String name){
        return bookService.searchBookOrAuthor(name);
    }

    @GetMapping(path = "/searchBookPage",params = "name")
    public Page<Book> searchBookOrAuthorPage(@RequestParam String name,@RequestHeader(name = "page", defaultValue = "0", required = false) int page,
                                             @RequestHeader(name = "page-size", defaultValue = "6", required = false) int size){

        return bookService.searchBookOrAuthorPage(name,page,size);
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
