package com.example.books.repository.jpa;

import com.example.books.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserAllBooksWithFavJpaRepository extends JpaRepository<UserAllBooksWithFav, UserAllBooksWithFavKey> {
    @Query("select b from UserAllBooksWithFav b where b.user like :user")
    List<UserAllBooksWithFav> getAllBooksWithFavUser(User user);

    @Query("select b from UserAllBooksWithFav b where b.user like :user and b.book like :book")
    Optional<UserAllBooksWithFav> findByIdAllBooksFav(User user, Book book);

    @Query("select  u.inFavourite from UserAllBooksWithFav u where u.book like :book and u.user like :user")
    int getInFavouritesBook(User user, Book book);

    @Query("select u.user from UserAllBooksWithFav u where u.user like :user")
    User findUser(User user);

   // select b from Book b join b.author author where author.isDeleted like 0 and b.isDeleted=0
    @Query("select distinct u from UserAllBooksWithFav u join Book b on u.book like b")
    List<UserAllBooksWithFav> findAllBooks();

    @Query("select u from UserAllBooksWithFav u where u.book like :book and u.user like :user")
    List<UserAllBooksWithFav> searchBookOrAuthor(Book book,User user);

    @Query("select u.user from UserAllBooksWithFav u")
    List<User> listAllUserFavBooks();
}
