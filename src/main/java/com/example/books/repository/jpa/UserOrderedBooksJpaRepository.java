package com.example.books.repository.jpa;

import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.model.userOrdered;
import com.example.books.model.userOrderedBooksKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserOrderedBooksJpaRepository extends JpaRepository<userOrdered, userOrderedBooksKey> {

    @Query("select distinct u.book from userOrdered u")
    List<Book> getAllBooks();

    @Query("select distinct u.book from userOrdered u where u.user like :user")
    List<Book> getAllBooksOrderedUser(User user);

    @Query("select  u.status from userOrdered u where u.book like :name")
    int getStatusBookOrdered(Book name);
}
