package com.example.books.repository;

import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.model.UserAllBooksWithFav;
import com.example.books.model.UserFavouriteBooks;
import com.example.books.model.paginate.Page;

import java.util.List;
import java.util.Optional;

public interface UserAllBooksWithFavRepository {
    List<UserAllBooksWithFav> getAllBooks();
    Optional<UserAllBooksWithFav> findById(User user,Book book);
    UserAllBooksWithFav save(UserAllBooksWithFav UserAllBooksWithFav);

    void saveAllBooks(User user, List<Book> getAllBooks);

    List<UserAllBooksWithFav> getAllBooksWithFavUser(User user);

    User findUser(User user);

    List<UserAllBooksWithFav> searchBookOrAuthor(List<Book> book,User user);


    void saveBookForEachUser(Book book);
}
