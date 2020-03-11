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
    List<User> listUsers();
    User getByUserName(String userName);
   // User createUser(String userName,String name, String surname, String address, String number,String password, String passwordConfirm, String email,Roles roles);
    void deleteUser(Long id);
    Optional<User> findById(Long id);
   // User editUser(String userName,String name, String surname, String address, String number,String password, String passwordConfirm, String email,Roles roles);
    List<String> findUsers(List<Long> idList);
    User save(User user);
    User editUser(Long id,String userName,String name, String surname, String address, String number, String email);
    long findAnotherSameUserName(String userName,Long id);
    User editUserImg(Long id,String userName,String name, String surname, String address, String number, String email,byte[] file);



    void deleteFavBook(Long id, Book book);

    User addFavouriteBook(User user);



    void deleteOrderedBook(Long id, Book book);



    userOrdered addFavouriteBookStatus(User user, Book book);

    List<Book> getAllRequestsOrdersStatus();

    List<Book> allBooksOrderedStatus(Long id);

    int getStatusBookOrdered(User user,Book name);

 void deleteOrderedBookStatus(Long id, Book book);

    UserFavouriteBooks addFavouriteBookForUser(User user, Book book);

    void deleteFavouriteBookUser(Long id, Book book);

    List<Book> getAllFavouriteBooksUser(Long id);

    int getStatusOrderedFavouriteBook(User user,Book book);

    User getUserByBook(User user,Book book);

    userOrdered approveOrder(User user, Book book);

    userOrdered declineOrder(User user, Book book);

    List<userOrdered> getAllRequests();

    Page<userOrdered> getAllRequestsPaginate(int page, int size);

    Page<Book> allOrderedBooksStatus(int page, int size, Long id);

    Page<Book> getAllFavouriteBooksUserPaginate(int page, int size,Long id);
}
