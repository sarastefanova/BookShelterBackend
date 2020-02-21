package com.example.books.repository.jpa;

import com.example.books.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorJpaRepository extends JpaRepository<Author,String> {

    @Query("select a from Author a where a.nameAndSurname like :authorName")
    Author checkArturName(String authorName);

    @Query("select a.nameAndSurname from Author a")
    List<String> getAllNamesOfAuthors();

    @Query("select count(a.nameAndSurname) from Author a where lower(a.nameAndSurname) like lower(:userName) group by a.nameAndSurname")
    Long findAnotherSameAuthor(String userName);
}
