package com.example.books.repository.jpa;

import com.example.books.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RolesJpaRepository extends JpaRepository<Roles,Long> {

    @Query("select R from Roles R where R.role like :roles")
    Roles checkIfExcistRole(String roles);
}
