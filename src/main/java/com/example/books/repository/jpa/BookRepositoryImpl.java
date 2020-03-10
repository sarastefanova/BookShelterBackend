package com.example.books.repository.jpa;

import com.example.books.model.*;
import com.example.books.model.exceptions.InvalidBookId;
import com.example.books.model.exceptions.InvalidUserId;
import com.example.books.model.paginate.Page;
import com.example.books.repository.BookRepository;
import com.example.books.repository.UserAllBooksWithFavRepository;
import com.example.books.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final BookJpaRepository bookJpaRepository;
    private final UserAllBooksWithFavRepository userAllBooksWithFavRepository;
    private final UserRepository userRepository;

    public BookRepositoryImpl(BookJpaRepository bookJpaRepository, UserAllBooksWithFavRepository userAllBooksWithFavRepository, UserRepository userRepository) {
        this.bookJpaRepository = bookJpaRepository;
        this.userAllBooksWithFavRepository = userAllBooksWithFavRepository;
        this.userRepository = userRepository;
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
         Book book=this.bookJpaRepository.findById(name).orElseThrow(InvalidBookId::new);
         book.setIsDeleted(1);
        //this.bookJpaRepository.deleteById(name);
        this.bookJpaRepository.save(book);
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

    @Override
    public Author getAuthorByBook(String name) {
        return this.bookJpaRepository.getAuthorByBook(name);
    }

    @Override
    public Page<UserFavouriteBooks> getAllBooksAuthorFavourite(int page,int size,User user) {

        List<UserFavouriteBooks>getBooks=this.bookJpaRepository.getAllBooksAuthorFavourite(user);
        int i=0;
        return Page.slice(getBooks,page,size);
    }

    @Override
    public Page<UserAllBooksWithFav> getAllBooksUserWithFav(int page, int size, Long id) {
       // List<Book>newBooks=this.bookJpaRepository.findAllAuthors();//tuka gi imam site knigi

        List<UserAllBooksWithFav>userAllBooksWithFavs=new ArrayList<>();
        if(id==0){
            User user =this.userAllBooksWithFavRepository.getAllBooks().get(0).getUser();
            userAllBooksWithFavs=this.userAllBooksWithFavRepository.getAllBooksWithFavUser(user);

        }else {
            User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);
            userAllBooksWithFavs=this.userAllBooksWithFavRepository.getAllBooksWithFavUser(user);
        }
        return Page.slice(userAllBooksWithFavs,page,size);

    }

    @Override
    public List<Book> getNewestBooks() {
        return this.bookJpaRepository.getNewestBooks().subList(0,3);
    }
}
