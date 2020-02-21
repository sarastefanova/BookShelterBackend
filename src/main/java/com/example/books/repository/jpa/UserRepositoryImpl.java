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
    public Optional<User> findById(Long id) {
        return this.userJpaRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return this.userJpaRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        this.userJpaRepository.deleteById(id);
    }

    @Override
    public List<String> findByIdList(List<Long> idList) {
        return this.userJpaRepository.findByIdList(idList);
    }

    @Override
    public User findByUserName(String userName) {
        return this.userJpaRepository.findUserByUserName(userName);
    }

    @Override
    public Long findAnotherSameUserName(String userName,Long id) {
        return this.userJpaRepository.findAnotherSameUserName(userName,id);
    }
}
