package com.example.books.repository.jpa;

import com.example.books.model.*;
import com.example.books.model.exceptions.InvalidBookId;
import com.example.books.model.exceptions.InvalidFavouriteBookId;
import com.example.books.model.exceptions.InvalidUserId;
import com.example.books.model.paginate.Page;
import com.example.books.repository.*;
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
    private final UserAllBooksWithFavJpaRepository userAllBooksWithFavJpaRepository;
    private final UserFavouriteBooksRepository userFavouriteBooksRepository;
    private final UserOrderedBooks userOrderedBooks;

    public BookRepositoryImpl(BookJpaRepository bookJpaRepository, UserAllBooksWithFavRepository userAllBooksWithFavRepository, UserRepository userRepository, UserAllBooksWithFavJpaRepository userAllBooksWithFavJpaRepository, UserFavouriteBooksRepository userFavouriteBooksRepository, UserOrderedBooks userOrderedBooks) {
        this.bookJpaRepository = bookJpaRepository;
        this.userAllBooksWithFavRepository = userAllBooksWithFavRepository;
        this.userRepository = userRepository;
        this.userAllBooksWithFavJpaRepository = userAllBooksWithFavJpaRepository;
        this.userFavouriteBooksRepository = userFavouriteBooksRepository;
        this.userOrderedBooks = userOrderedBooks;
    }

    @Override
    public Optional<Book> findById(String name) {
        return this.bookJpaRepository.findById(name);
    }

    @Override
    public Page<Book> getAllBooks(int page, int size) {
        org.springframework.data.domain.Page<Book> result=this.bookJpaRepository.findAll(PageRequest.of(page, size));

        List<Book>newBooks=this.bookJpaRepository.findAllAuthors();

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
        this.removeBookFromAllUsers(this.userAllBooksWithFavJpaRepository.listAllUserFavBooks(),book);
        this.removeBooksFromFavourite(this.userFavouriteBooksRepository.getAllUsersFavouriteBooks(),book);
        this.bookJpaRepository.save(book);
    }

    public void removeBooksFromFavourite(List<User> users, Book book){
        for (User u:users) {
           UserFavouriteBooks userFavouriteBooks=this.userFavouriteBooksRepository.findFavBookUser(u, book);
           if(userFavouriteBooks!=null){
               this.userFavouriteBooksRepository.deleteFavouriteBook(u, book);
               userOrdered userOrderedBooks=this.userOrderedBooks.findUserOrder(u, book);

               if(userOrderedBooks!=null){
                   this.userOrderedBooks.deleteOrder(u, book);
               }
           }
        }
    }

    public void removeBookFromAllUsers(List<User> users, Book book){
        for (User u:users) {
            UserAllBooksWithFavKey userAllBooksWithFavKey=new UserAllBooksWithFavKey(u.getId(),book.getName());
            UserAllBooksWithFav userAllBooksWithFav=this.userAllBooksWithFavJpaRepository.findById(userAllBooksWithFavKey).orElseThrow(InvalidFavouriteBookId::new);
            this.userAllBooksWithFavJpaRepository.delete(userAllBooksWithFav);
        }
    }

    @Override
    public List<Book> getAllBookByAuthor(String nameAndSurname) {
        return this.bookJpaRepository.getAllBookByAuthor(nameAndSurname);
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
    public Author getAuthorByBook(String name) {
        return this.bookJpaRepository.getAuthorByBook(name);
    }

    @Override
    public Page<UserFavouriteBooks> getAllBooksAuthorFavourite(int page,int size,User user) {
        List<UserFavouriteBooks>getBooks=this.bookJpaRepository.getAllBooksAuthorFavourite(user);
        return Page.slice(getBooks,page,size);
    }

    @Override
    public Page<UserAllBooksWithFav> getAllBooksUserWithFav(int page, int size, Long id) {
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
