package com.example.books.controller;

import com.example.books.model.Roles;
import com.example.books.model.User;
import com.example.books.model.exceptions.RolesNotFoundException;
import com.example.books.model.exceptions.UserAlreadyExists;
import com.example.books.service.RolesService;
import com.example.books.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/users",produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class RestUserController {

    private final UserService service;
    private final RolesService rolesService;

    public RestUserController(UserService service, RolesService rolesService) {
        this.service = service;
        this.rolesService = rolesService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createPizza(@RequestParam(value="userName") String userName,
                            @RequestParam(value="name") String name,
                            @RequestParam(value="surname") String surname,
                            @RequestParam(value="address",required = false)String address,
                            @RequestParam(value = "number")String number,
                            @RequestParam(value = "password")String password,
                            @RequestParam(value = "passwordConfirm",required = false)String passwordConfirm,
                            @RequestParam(value = "email",required = false)String email,
                            @RequestParam(value = "roles",required = false)String roles) throws UserAlreadyExists, RolesNotFoundException {

        User checkUser=this.service.getByUserName(userName);
        if(checkUser!=null)throw new UserAlreadyExists("User already exists");

        Roles newRole=this.rolesService.checkIfExcistRole(roles);
        if(newRole!=null){
            return service.createUser(userName,name,surname,address,number,password,passwordConfirm,email,newRole);
        }
        throw new RolesNotFoundException();

    }

    @GetMapping
    public List<User> getAllUsers(){
        return this.service.listUsers();
    }

    @PatchMapping("/{userName}")
    public User editUser(@PathVariable String userName,
                         @RequestParam(value="name") String name,
                         @RequestParam(value="surname") String surname,
                         @RequestParam(value="address",required = false)String address,
                         @RequestParam(value = "number")String number,
                         @RequestParam(value = "password")String password,
                         @RequestParam(value = "passwordConfirm",required = false)String passwordConfirm,
                         @RequestParam(value = "email",required = false)String email,
                         @RequestParam(value = "roles",required = false)String roles) throws RolesNotFoundException {

        Roles newRole=this.rolesService.checkIfExcistRole(roles);
        if(newRole!=null){
            return service.editUser(userName,name,surname,address,number,password,passwordConfirm,email,newRole);
        }
        throw new RolesNotFoundException();

    }

    @GetMapping(params = "userName")
    public User searchByName(@RequestParam String userName){
        return service.getByUserName(userName);
    }

    @DeleteMapping("/{userName}")
    public void deletePizza(@PathVariable String userName){
        this.service.deleteUser(userName);
    }
}
