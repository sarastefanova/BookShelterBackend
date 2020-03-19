package com.example.books.service;

import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.model.UserFavouriteBooks;
import com.example.books.model.paginate.Page;
import com.example.books.model.userOrdered;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User getByUserName(String userName);
    Optional<User> findById(Long id);
    List<String> findUsers(List<Long> idList);
    User save(User user);
    User editUser(Long id,String userName,String name, String surname, String address, String number, String email,byte[] file);
    userOrdered addOrderedBookUser(User user, Book book);
    int getStatusBookOrdered(User user,Book name);
    void deleteOrderedBook(Long id, Book book);
    UserFavouriteBooks addFavouriteBookForUser(User user, Book book);
    void deleteFavouriteBookUser(Long id, Book book);
    int getStatusOrderedFavouriteBook(User user,Book book);
    User getUserByBook(User user,Book book);
    userOrdered approveOrder(User user, Book book);
    userOrdered declineOrder(User user, Book book);
    Page<userOrdered> getAllRequestsPaginate(int page, int size);
    Page<Book> allOrderedBooks(int page, int size, Long id);
    Page<UserFavouriteBooks> getFavouriteBooksUserPaginate(int page, int size, Long id);
}
