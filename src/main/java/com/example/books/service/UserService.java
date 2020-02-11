package com.example.books.service;

import com.example.books.model.Roles;
import com.example.books.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> listUsers();
    User getByUserName(String userName);
    User createUser(String userName,String name, String surname, String address, String number,String password, String passwordConfirm, String email,Roles roles);
    void deleteUser(String  userName);

    User editUser(String userName,String name, String surname, String address, String number,String password, String passwordConfirm, String email,Roles roles);

}
