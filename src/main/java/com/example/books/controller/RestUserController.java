package com.example.books.controller;

import com.example.books.model.Roles;
import com.example.books.model.User;
import com.example.books.service.RolesService;
import com.example.books.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/user",produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class RestUserController {

    private final UserService userService;
    private final RolesService rolesService;

    public RestUserController(UserService userService, RolesService rolesService) {
        this.userService = userService;
        this.rolesService = rolesService;
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public User createPizza(@RequestParam(value="userName") String userName,
//                            @RequestParam(value="name") String name,
//                            @RequestParam(value="surname") String surname,
//                            @RequestParam(value="address",required = false)String address,
//                            @RequestParam(value = "number")String number,
//                            @RequestParam(value = "password")String password,
//                            @RequestParam(value = "passwordConfirm",required = false)String passwordConfirm,
//                            @RequestParam(value = "email",required = false)String email,
//                            @RequestParam(value = "roles",required = false)String roles) throws UserAlreadyExists, RolesNotFoundException {
//
//        User checkUser=this.service.getByUserName(userName);
//        if(checkUser!=null)throw new UserAlreadyExists("User already exists");
//
//        Roles newRole=this.rolesService.checkIfExcistRole(roles);
//        if(newRole!=null){
//            return service.createUser(userName,name,surname,address,number,password,passwordConfirm,email,newRole);
//        }
//        throw new RolesNotFoundException();
//
//    }

//    @GetMapping
//    public List<User> getAllUsers(){
//        return this.service.listUsers();
//    }
//
//    @PatchMapping("/{userName}")
//    public User editUser(@PathVariable String userName,
//                         @RequestParam(value="name") String name,
//                         @RequestParam(value="surname") String surname,
//                         @RequestParam(value="address",required = false)String address,
//                         @RequestParam(value = "number")String number,
//                         @RequestParam(value = "password")String password,
//                         @RequestParam(value = "passwordConfirm",required = false)String passwordConfirm,
//                         @RequestParam(value = "email",required = false)String email,
//                         @RequestParam(value = "roles",required = false)String roles) throws RolesNotFoundException {
//
//        Roles newRole=this.rolesService.checkIfExcistRole(roles);
//        if(newRole!=null){
//            return service.editUser(userName,name,surname,address,number,password,passwordConfirm,email,newRole);
//        }
//        throw new RolesNotFoundException();
//
//    }
//
//    @GetMapping(params = "userName")
//    public User searchByName(@RequestParam String userName){
//        return service.getByUserName(userName);
//    }
//
//    @DeleteMapping("/{userName}")
//    public void deletePizza(@PathVariable String userName){
//        this.service.deleteUser(userName);
//    }

    @GetMapping(params = "id")
    public Optional<User> searchById(@RequestParam Long id){
                return userService.findById(id);
    }


    @PatchMapping("/{id}")
    public User updateUser(
            @PathVariable(value="id") Long id,
            @RequestParam(value = "userName") String userName,
            @RequestParam(value = "name")String name,
            @RequestParam(value = "surname")String surname,
            @RequestParam(value = "address")String address,
            @RequestParam(value = "number")String number,
            @RequestParam(value = "email")String email)  {


            return (this.userService.editUser(id,userName,name,surname,address,number,email));
    }

    @PatchMapping("/editUser/{id}")
    public User updateUser(
            @PathVariable(value="id") Long id,
            @RequestParam(value = "userName") String userName,
            @RequestParam(value = "name")String name,
            @RequestParam(value = "surname")String surname,
            @RequestParam(value = "address")String address,
            @RequestParam(value = "number")String number,
            @RequestParam(value = "email")String email,
            @RequestParam(value = "file",required = false) MultipartFile file) throws IOException {

        if(file==null){
            User user=this.userService.findById(id).get();
            return (this.userService.editUserImg(id,userName,name,surname,address,number,email,user.getFile()));
        }
        return (this.userService.editUserImg(id,userName,name,surname,address,number,email,file.getBytes()));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        if(userService.getByUserName(user.getUsername()) != null){
            //Status code 409
            return  new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Roles role=new Roles();
        role=rolesService.checkIfExcistRole("user");
        int i=0;
        user.setRoles(role);
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<?> getUser(Principal principal){
        //Principal principal = request.getUserPrincipal();
        if(principal == null || principal.getName() == null){
            //This means; logout will be successful. login?logout
            return new ResponseEntity<>(HttpStatus.OK);
        }
        //username = principal.getName()
        return ResponseEntity.ok(userService.getByUserName(principal.getName()));
    }

    @PostMapping("/names")
    public ResponseEntity<?> getNamesOfUsers(@RequestBody List<Long> idList){
        return ResponseEntity.ok(userService.findUsers(idList));
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("It is working...");
    }
}
