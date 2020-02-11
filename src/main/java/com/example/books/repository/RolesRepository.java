package com.example.books.repository;

import com.example.books.model.Roles;


public interface RolesRepository {

    Roles save(Roles roles);
    void deleteById(Long id);

    Roles checkIfExcistRole(String roles);
}
