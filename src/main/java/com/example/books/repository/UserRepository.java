package com.example.books.repository;

import com.example.books.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> getAllUsers();
    Optional<User> findById(String userName);
    User save(User user);
    void deleteById(String userName);


    User findByUserName(String userName);
}
