package com.example.books.repository.jpa;

import com.example.books.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<User,String> {

    @Query("SELECT U from User U WHERE U.userName like :userName")
    User findUserByUserName(String userName);
}
