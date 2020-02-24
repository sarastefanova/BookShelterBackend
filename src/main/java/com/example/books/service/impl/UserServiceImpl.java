package com.example.books.service.impl;

import com.example.books.model.Book;
import com.example.books.model.Roles;
import com.example.books.model.User;
import com.example.books.model.exceptions.InvalidUserId;
import com.example.books.model.exceptions.UserAlreadyExists;
import com.example.books.repository.UserRepository;
import com.example.books.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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



//    @Override
//    public User createUser(String userName,String name, String surname, String address, String number, String password, String passwordConfirm, String email,Roles roles) {
//
//
//        User user=new User(userName,name,surname,address,number,passwordEncoder.encode(password),passwordConfirm,email,roles);
//        return this.userRepository.save(user);
//    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }




//    @Override
//    public User editUser(String userName,String name, String surname, String address, String number, String password, String passwordConfirm, String email,Roles roles) {
//        User user=this.userRepository.findByUserName(userName);
//        user.setAddress(address);
//        user.setEmail(email);
//        user.setName(name);
//        user.setSurname(surname);
//        user.setNumber(number);
//        user.setRoles(roles);
//        user.setPassword(password);
//        user.setPasswordConfirm(passwordConfirm);
//
//        return this.userRepository.save(user);
//    }

    @Override
    public List<String> findUsers(List<Long> idList) {
        return userRepository.findByIdList(idList);
    }

    @Override
    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User editUser(Long id, String userName, String name, String surname, String address, String number,  String email) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);

        if((this.userRepository.findAnotherSameUserName(userName,id))==null){
            user.setUserName(userName);
            user.setName(name);
            user.setRoles(user.getRoles());
            user.setPassword(user.getPassword());
            user.setAddress(address);
            user.setEmail(email);
            user.setSurname(surname);
            user.setNumber(number);
            user.setId(id);
            return this.userRepository.save(user);
        }else {
          throw new UserAlreadyExists();
        }





    }

    @Override
    public long findAnotherSameUserName(String userName,Long id) {
        return this.userRepository.findAnotherSameUserName(userName,id);
    }

    @Override
    public User editUserImg(Long id, String userName, String name, String surname, String address, String number, String email, byte[] file) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);

        if((this.userRepository.findAnotherSameUserName(userName,id))==null){
            user.setUserName(userName);
            user.setName(name);
            user.setRoles(user.getRoles());
            user.setPassword(user.getPassword());
            user.setAddress(address);
            user.setEmail(email);
            user.setSurname(surname);
            user.setNumber(number);
            user.setId(id);
            if(file!=null){
                user.setFile(file);
            }else {
                user.setFile(user.getFile());
            }

            return this.userRepository.save(user);
        }else {
            throw new UserAlreadyExists();
        }




    }

    @Override
    public List<Book> allFavouriteBooksOfUser(Long id) {
        return this.userRepository.allFavouriteBooksOfUser(id);
    }

    @Override
    public void deleteFavBook(Long id, Book book) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);
        List<Book>allBooksLiked=user.getLikedBooks();
        if(allBooksLiked.contains(book)){
            allBooksLiked.remove(book);
        }
        user.setLikedBooks(allBooksLiked);
        this.userRepository.save(user);

    }

    @Override
    public User addFavouriteBook(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUserName(s);
    }



}
