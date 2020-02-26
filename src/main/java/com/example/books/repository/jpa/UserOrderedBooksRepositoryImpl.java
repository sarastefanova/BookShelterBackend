package com.example.books.repository.jpa;

import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.model.userOrdered;
import com.example.books.repository.UserOrderedBooks;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public int getStatusBookOrdered(Book name) {

        return this.userOrderedBooksJpaRepository.getStatusBookOrdered(name);
    }
}
