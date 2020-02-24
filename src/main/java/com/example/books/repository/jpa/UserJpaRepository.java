package com.example.books.repository.jpa;

import com.example.books.model.Book;
import com.example.books.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserJpaRepository extends JpaRepository<User,Long> {

    @Query("SELECT U from User U WHERE U.userName like :userName")
    User findUserByUserName(String userName);


        @Query("select u.name from User u where u.id  in (:pIdList)")
        List<String> findByIdList(@Param("pIdList") List<Long> idList);

     @Query("select count(u.userName) from User u where u.userName like :userName and u.id not like :id group by u.userName")
    Long findAnotherSameUserName(String userName,Long id);

     @Query("select u.likedBooks from User u join u.likedBooks likedBooks where u.id like :id")
    List<Book> allFavouriteBooksOfUser(Long id);
}
