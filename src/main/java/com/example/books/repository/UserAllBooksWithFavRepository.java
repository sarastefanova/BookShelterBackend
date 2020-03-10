package com.example.books.repository;

import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.model.UserAllBooksWithFav;
import com.example.books.model.paginate.Page;

import java.util.List;
import java.util.Optional;

public interface UserAllBooksWithFavRepository {
    List<UserAllBooksWithFav> getAllBooks();
    Optional<UserAllBooksWithFav> findById(User user,Book book);
    Page<UserAllBooksWithFav> getAllBooks(int page, int size);
    UserAllBooksWithFav save(UserAllBooksWithFav UserAllBooksWithFav);
    void deleteById(User user, Book book);

    List<UserAllBooksWithFav>  saveAll(List<UserAllBooksWithFav> UserAllBooksWithFav);

    void saveAllBooks(User user, List<Book> getAllBooks);

    List<UserAllBooksWithFav> getAllBooksWithFavUser(User user);

    int getInFavouritesBook(User user, Book book);

    User findUser(User user);

    List<UserAllBooksWithFav> searchBookOrAuthor(Book book,User user);

    void saveBookForEachUser(Book book);
}
