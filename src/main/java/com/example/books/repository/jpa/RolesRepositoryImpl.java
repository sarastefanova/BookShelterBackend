package com.example.books.repository.jpa;

import com.example.books.model.Roles;
import org.springframework.stereotype.Repository;

@Repository
public class RolesRepositoryImpl implements com.example.books.repository.RolesRepository {

    private final RolesJpaRepository rolesJpaRepository;

    public RolesRepositoryImpl(RolesJpaRepository rolesJpaRepository) {
        this.rolesJpaRepository = rolesJpaRepository;
    }

    @Override
    public Roles save(Roles roles) {
        return this.rolesJpaRepository.save(roles);
    }

    @Override
    public void deleteById(Long id) {
        this.rolesJpaRepository.deleteById(id);
    }

    @Override
    public Roles checkIfExcistRole(String roles) {
        return this.rolesJpaRepository.checkIfExcistRole(roles);
    }
}
