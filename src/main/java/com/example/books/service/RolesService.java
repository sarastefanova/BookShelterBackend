package com.example.books.service;

import com.example.books.model.Roles;



public interface RolesService {
    Roles createRoles(Roles roles);
    Roles checkIfExcistRole(String roles);
}
