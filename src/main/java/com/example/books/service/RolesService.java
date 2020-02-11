package com.example.books.service;

import com.example.books.model.Roles;



public interface RolesService {
    Roles createRoles(Roles roles);
    void deleteRoles(Long id);

    Roles checkIfExcistRole(String roles);
}
