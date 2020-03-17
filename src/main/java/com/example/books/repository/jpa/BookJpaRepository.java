package com.example.books.repository.jpa;

import com.example.books.model.Author;
import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.model.UserFavouriteBooks;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookJpaRepository extends JpaRepository<Book,String> {
    @Query("select b from Book b where b.name like :name")
    Book checkIfBookExists(String name);

    @Query("select b from Book b where b.author.nameAndSurname like :nameAndSurname and b.isDeleted=0")
    List<Book> getAllBookByAuthor(String nameAndSurname);
    //select b from Book b where b.name like :name or b.author.nameAndSurname like :name
    @Query("select b from Book b where b.name like %:name% or b.author.nameAndSurname like %:name%")
    List<Book> findByName(String name);

    @Query("select count(b.name) from Book b where b.name like :userName group by b.name")
    Long findAnotherSameUserName(String userName);

    @Query("select b from Book b join b.author author where author.isDeleted like 0 and b.isDeleted=0")
    List<Book> findAllAuthors();

    @Query("select b.author from Book b where b.name like :name")
    Author getAuthorByBook(String name);

    @Query("select u from  Book b left join UserFavouriteBooks u  on u.book like b")
    List<UserFavouriteBooks> getAllBooksAuthorFavourite(User user);

    @Query("select b from Book b where b.isDeleted=0")
    List<Book> findAllBooks();

    @Query("select b from Book b where b.isDeleted=0 order by b.createDateTime desc")
    List<Book> getNewestBooks();



}
