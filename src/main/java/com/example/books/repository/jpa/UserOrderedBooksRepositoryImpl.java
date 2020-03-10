package com.example.books.repository.jpa;

import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.model.exceptions.InvalidBookOrderKye;
import com.example.books.model.userOrdered;
import com.example.books.model.userOrderedBooksKey;
import com.example.books.repository.UserOrderedBooks;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserOrderedBooksRepositoryImpl implements UserOrderedBooks {

    private final UserOrderedBooksJpaRepository userOrderedBooksJpaRepository;

    public UserOrderedBooksRepositoryImpl(UserOrderedBooksJpaRepository userOrderedBooksJpaRepository) {
        this.userOrderedBooksJpaRepository = userOrderedBooksJpaRepository;
    }

    @Override
    public userOrdered userOrderedSave(userOrdered userOrdered) {
        return this.userOrderedBooksJpaRepository.save(userOrdered);
    }

    @Override
    public List<Book> getAllOrders() {
        return this.userOrderedBooksJpaRepository.getAllBooks();
    }

    @Override
    public List<Book> getAllBooksOrderedUser(User user) {
        return this.userOrderedBooksJpaRepository.getAllBooksOrderedUser(user);
    }

    @Override
    public Optional<userOrdered> findById(userOrderedBooksKey userOrderedKey) {
        return this.userOrderedBooksJpaRepository.findById(userOrderedKey);
    }


    @Override
    public int getStatusBookOrdered(User user,Book name) {

        return this.userOrderedBooksJpaRepository.getStatusBookOrdered(user,name);
    }

    @Override
    public void deleteOrder(User user, Book book) {
        userOrdered userOrdered=this.findByIdOrdered(user,book);
        this.userOrderedBooksJpaRepository.delete(userOrdered);
    }

    public userOrdered findByIdOrdered(User user, Book book) {
      return   this.userOrderedBooksJpaRepository.findByIdOrdered(user,book);
    }

    @Override
    public User getUserByBook(User user,Book book) {
        return this.userOrderedBooksJpaRepository.getUserByBook(user,book);
    }

    @Override
    public userOrdered findUserOrder(User user, Book book) {
        return this.userOrderedBooksJpaRepository.findUserOrder(user,book);
    }

    @Override
    public List<userOrdered> getAllRequests() {
        return this.userOrderedBooksJpaRepository.getAllRequests();
    }
}
