package com.example.books.controller;

import com.example.books.model.*;
import com.example.books.model.exceptions.InvalidBookId;
import com.example.books.model.exceptions.InvalidUserId;
import com.example.books.model.exceptions.ListContainsBook;
import com.example.books.model.paginate.Page;
import com.example.books.repository.UserOrderedBooks;
import com.example.books.service.BookService;
import com.example.books.service.RolesService;
import com.example.books.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
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
    private final BookService bookService;
    public RestUserController(UserService userService, RolesService rolesService, BookService bookService) {
        this.userService = userService;
        this.rolesService = rolesService;
        this.bookService = bookService;
    }

    @GetMapping(params = "id")
    public Optional<User> searchById(@RequestParam Long id){
                return userService.findById(id);
    }

    @GetMapping(path = "/getFavouriteBooksUserPaginate/{id}")//ovaa kje go koristeme za proba sega da vrakjame za allfavs books lista od gav books a ne samo books
    public Page<UserFavouriteBooks> getFavouriteBooksUserPaginate(@RequestHeader(name = "page", defaultValue = "0", required = false) int page,
                                                       @RequestHeader(name = "page-size", defaultValue = "10", required = false)int size,
                                                       @PathVariable(value = "id")Long id){
        return this.userService.getFavouriteBooksUserPaginate(page,size,id);
    }

    @GetMapping(path = "/getStatusBookOrdered/{id}/{name}")
    public int getStatusBookOrdered(@PathVariable(value = "id")Long id,@PathVariable(value = "name")String name){
        Book book=this.bookService.getById(name).orElseThrow(InvalidBookId::new);
        User user=this.userService.findById(id).orElseThrow(InvalidUserId::new);
        return this.userService.getStatusBookOrdered(user,book);
    }


    @GetMapping(path = "/getStatusOrderedFavouriteBook/{id}/{name}")
    public int getStatusOrderedFavouriteBook(@PathVariable(value = "id")Long id,@PathVariable(value = "name")String name){
        Book book=this.bookService.getById(name).orElseThrow(InvalidBookId::new);
        User user=this.userService.findById(id).orElseThrow(InvalidUserId::new);
        return this.userService.getStatusOrderedFavouriteBook(user,book);
    }


    @GetMapping(path = "/getAllRequestsPaginate")
    public Page<userOrdered> getAllRequestsPaginate(@RequestHeader(name = "page", defaultValue = "0", required = false) int page,
                                                    @RequestHeader(name = "page-size", defaultValue = "10", required = false) int size){

        return this.userService.getAllRequestsPaginate(page,size);
    }

    @GetMapping(path = "/allOrderedBooksPaginate/{id}")
    public Page<Book> allOrderedBooksPaginate(@RequestHeader(name = "page", defaultValue = "0", required = false) int page,
                                                    @RequestHeader(name = "page-size", defaultValue = "10", required = false) int size,
                                                           @PathVariable(value = "id")Long id){

        return this.userService.allOrderedBooks(page,size,id);
    }

    @DeleteMapping(path = "/deleteOrderedBookUser/{id}",params = "name")
    public void deleteOrderedBookStatus(@PathVariable(value ="id")Long id,
                                  @RequestParam(value = "name")String name){
        Book book=this.bookService.getById(name).orElseThrow(InvalidBookId::new);
        this.userService.deleteOrderedBook(id,book);
    }

    @DeleteMapping(path = "/deleteFavouriteBookUser/{id}",params = "name")
    public void deleteFavouriteBookUser(@PathVariable(value ="id")Long id,
                                        @RequestParam(value = "name")String name){
        Book book=this.bookService.getById(name).orElseThrow(InvalidBookId::new);
        this.userService.deleteFavouriteBookUser(id,book);
    }

    @PatchMapping("/{id}")
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
            return (this.userService.editUser(id,userName,name,surname,address,number,email,user.getFile()));
        }
        return (this.userService.editUser(id,userName,name,surname,address,number,email,file.getBytes()));
    }

    @PatchMapping(path = "/addFavouriteBook/{id}/{name}")
    public UserFavouriteBooks userFavouriteBooks(@PathVariable(value="id") Long id,
                                   @PathVariable(value = "name")String name){
        User user=this.userService.findById(id).orElseThrow(InvalidUserId::new);
        Book book=this.bookService.getById(name).orElseThrow(InvalidBookId::new);
        return this.userService.addFavouriteBookForUser(user,book);
    }


    @PatchMapping(path = "/addOrderedBook/{id}/{name}")
    public userOrdered addOrderedBook(@PathVariable(value="id") Long id,
                                          @PathVariable(value = "name")String name){
        User user=this.userService.findById(id).orElseThrow(InvalidUserId::new);
        Book book=this.bookService.getById(name).orElseThrow(InvalidBookId::new);
        return this.userService.addOrderedBookUser(user,book);
    }

    @PatchMapping(path = "/approveOrder/{id}/{name}")
    public userOrdered approveOrder(@PathVariable(value = "id")Long id,
                                         @PathVariable(value = "name")String name){
        User user=this.userService.findById(id).orElseThrow(InvalidUserId::new);
        Book book=this.bookService.getById(name).orElseThrow(InvalidBookId::new);
        return this.userService.approveOrder(user,book);
    }

    @PatchMapping(path = "/declineOrder/{id}/{name}")
    public userOrdered declineOrder(@PathVariable(value = "id")Long id,
                                    @PathVariable(value = "name")String name){
        User user=this.userService.findById(id).orElseThrow(InvalidUserId::new);
        Book book=this.bookService.getById(name).orElseThrow(InvalidBookId::new);
        return this.userService.declineOrder(user,book);
    }

    @GetMapping(path = "/getUserByBook/{id}/{name}")
    public User getUserByBook(@PathVariable(value = "id")Long id,@PathVariable(value = "name")String name){
        User user=this.userService.findById(id).orElseThrow(InvalidUserId::new);
        Book book=this.bookService.getById(name).orElseThrow(InvalidBookId::new);
        return this.userService.getUserByBook(user,book);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        if(userService.getByUserName(user.getUsername()) != null){
            //Status code 409
            return  new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Roles role=rolesService.checkIfExcistRole("user");
        user.setRoles(role);
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<?> getUser(Principal principal){
        //Principal principal = request.getUserPrincipal();
        int j=0;
        if(principal == null || principal.getName() == null){
            //This means; logout will be successful. login?logout
            return new ResponseEntity<>(HttpStatus.OK);
        }
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
