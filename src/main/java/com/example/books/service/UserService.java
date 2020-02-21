package com.example.books.service;

import com.example.books.model.Roles;
import com.example.books.model.User;
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
}
