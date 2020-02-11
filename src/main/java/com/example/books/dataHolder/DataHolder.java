package com.example.books.dataHolder;

import com.example.books.model.Roles;
import com.example.books.repository.jpa.RolesJpaRepository;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class DataHolder {

    public static final List<Roles> roles = new ArrayList<>();

    private  final RolesJpaRepository rolesRepository;

    public DataHolder(RolesJpaRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @PostConstruct
    public void init(){
        Roles role=new Roles();
        role.setRole("admin");

        roles.add(role);

        role=new Roles();
        role.setRole("user");

        roles.add(role);

        this.rolesRepository.saveAll(roles);
    }
}
