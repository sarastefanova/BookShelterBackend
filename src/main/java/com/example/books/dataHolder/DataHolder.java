package com.example.books.dataHolder;

import com.example.books.model.Roles;
import com.example.books.model.User;
import com.example.books.repository.jpa.RolesJpaRepository;
import com.example.books.repository.jpa.UserJpaRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class DataHolder {

    public static final List<Roles> roles = new ArrayList<>();

    private  final RolesJpaRepository rolesRepository;
    private  final UserJpaRepository userJpaRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public DataHolder(RolesJpaRepository rolesRepository, UserJpaRepository userJpaRepository) {
        this.rolesRepository = rolesRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @PostConstruct
    public void init(){
//        Roles role=new Roles();
//        role.setRole("admin");
//
//        roles.add(role);
//
//        role=new Roles();
//        role.setRole("user");
//
//        roles.add(role);
//
//        this.rolesRepository.saveAll(roles);
//        User user=new User();
//        user.setName("Sara");
//        user.setSurname("Stefanova");
//        user.setNumber("078230568");
//        user.setEmail("sarasstefanova@gmail.com");
//        user.setPassword(bCryptPasswordEncoder.encode("sara"));
//        user.setUserName("stefanovaAdmin");
//        user.setAddress("Blagoja Strackov 6");
//        role=rolesRepository.checkIfExcistRole("admin");
//        user.setRoles(role);
        //this.userJpaRepository.save(user);
    }
}
