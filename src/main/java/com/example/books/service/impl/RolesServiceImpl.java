package com.example.books.service.impl;

import com.example.books.model.Roles;
import com.example.books.repository.RolesRepository;
import com.example.books.service.RolesService;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
public class RolesServiceImpl implements RolesService {

    private final RolesRepository repository;

    public RolesServiceImpl(RolesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Roles createRoles(Roles roles) {
        return repository.save(roles);
    }

    @Override
    public void deleteRoles(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public Roles checkIfExcistRole(String roles) {
        return this.repository.checkIfExcistRole(roles);
    }


}
