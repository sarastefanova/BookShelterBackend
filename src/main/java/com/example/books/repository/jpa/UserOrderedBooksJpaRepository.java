package com.example.books.repository.jpa;

import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.model.userOrdered;
import com.example.books.model.userOrderedBooksKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserOrderedBooksJpaRepository extends JpaRepository<userOrdered, userOrderedBooksKey> {


    @Query("select distinct u.book from userOrdered u where u.user like :user")
    List<Book> getAllBooksOrderedUser(User user);

    @Query("select  u.status from userOrdered u where u.book like :name and u.user like :user")
    int getStatusBookOrdered(User user,Book name);

    @Query("select  u.user from userOrdered u where u.book like :book and u.user like :user")
    User getUserByBook(User user,Book book);

    @Query("select distinct u from userOrdered u where u.book like :book and u.user like :user")
    userOrdered findUserOrder(User user, Book book);

    @Query("select  distinct  u from userOrdered u where u.isInRequests=0")
    List<userOrdered> getAllRequests();

    @Query("select  u from userOrdered u where u.book like :book and u.user like :user")
    userOrdered findByIdOrdered(User user, Book book);
}
