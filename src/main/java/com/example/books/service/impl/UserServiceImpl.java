package com.example.books.service.impl;

import com.example.books.model.Roles;
import com.example.books.model.User;
import com.example.books.repository.UserRepository;
import com.example.books.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listUsers() {
        return this.userRepository.getAllUsers();
    }

    @Override
    public User getByUserName(String userName) {
        return this.userRepository.findByUserName(userName);
    }



    @Override
    public User createUser(String userName,String name, String surname, String address, String number, String password, String passwordConfirm, String email,Roles roles) {


        User user=new User(userName,name,surname,address,number,passwordEncoder.encode(password),passwordConfirm,email,roles);
        return this.userRepository.save(user);
    }

    @Override
    public void deleteUser(String  userName) {
        this.userRepository.deleteById(userName);
    }



    @Override
    public User editUser(String userName,String name, String surname, String address, String number, String password, String passwordConfirm, String email,Roles roles) {
        User user=this.userRepository.findByUserName(userName);
        user.setAddress(address);
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setNumber(number);
        user.setRoles(roles);
        user.setPassword(password);
        user.setPasswordConfirm(passwordConfirm);

        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUserName(s);
    }



}
