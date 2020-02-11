package com.example.books.repository.jpa;

import com.example.books.model.User;
import com.example.books.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return this.userJpaRepository.findAll();
    }

    @Override
    public Optional<User> findById(String userName) {
        return this.userJpaRepository.findById(userName);
    }

    @Override
    public User save(User user) {
        return this.userJpaRepository.save(user);
    }

    @Override
    public void deleteById(String userName) {
        this.userJpaRepository.deleteById(userName);
    }


    @Override
    public User findByUserName(String userName) {
        return this.userJpaRepository.findUserByUserName(userName);
    }


}
