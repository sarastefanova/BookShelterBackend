package com.example.books.repository;

import com.example.books.model.Book;
import com.example.books.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> getAllUsers();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
    List<String> findByIdList(List<Long>idList);

    User findByUserName(String userName);

    Long findAnotherSameUserName(String userName,Long id);


}
