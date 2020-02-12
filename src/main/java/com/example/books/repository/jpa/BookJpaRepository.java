package com.example.books.repository.jpa;

import com.example.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookJpaRepository extends JpaRepository<Book,String> {
    @Query("select b from Book b where b.name like :name")
    Book checkIfBookExists(String name);
}
