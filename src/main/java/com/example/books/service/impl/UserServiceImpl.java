package com.example.books.service.impl;

import com.example.books.model.*;
import com.example.books.model.exceptions.*;
import com.example.books.model.paginate.Page;
import com.example.books.repository.BookRepository;
import com.example.books.repository.UserFavouriteBooksRepository;
import com.example.books.repository.UserOrderedBooks;
import com.example.books.repository.UserRepository;
import com.example.books.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private final UserOrderedBooks userOrderedBooks;

    private final UserFavouriteBooksRepository userFavouriteBooksRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BookRepository bookRepository, UserOrderedBooks userOrderedBooks, UserFavouriteBooksRepository userFavouriteBooksRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;


        this.userOrderedBooks = userOrderedBooks;

        this.userFavouriteBooksRepository = userFavouriteBooksRepository;
    }

    @Override
    public List<User> listUsers() {
        return this.userRepository.getAllUsers();
    }

    @Override
    public User getByUserName(String userName) {
        return this.userRepository.findByUserName(userName);
    }



//    @Override
//    public User createUser(String userName,String name, String surname, String address, String number, String password, String passwordConfirm, String email,Roles roles) {
//
//
//        User user=new User(userName,name,surname,address,number,passwordEncoder.encode(password),passwordConfirm,email,roles);
//        return this.userRepository.save(user);
//    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }




//    @Override
//    public User editUser(String userName,String name, String surname, String address, String number, String password, String passwordConfirm, String email,Roles roles) {
//        User user=this.userRepository.findByUserName(userName);
//        user.setAddress(address);
//        user.setEmail(email);
//        user.setName(name);
//        user.setSurname(surname);
//        user.setNumber(number);
//        user.setRoles(roles);
//        user.setPassword(password);
//        user.setPasswordConfirm(passwordConfirm);
//
//        return this.userRepository.save(user);
//    }

    @Override
    public List<String> findUsers(List<Long> idList) {
        return userRepository.findByIdList(idList);
    }

    @Override
    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User editUser(Long id, String userName, String name, String surname, String address, String number,  String email) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);

        if((this.userRepository.findAnotherSameUserName(userName,id))==null){
            user.setUserName(userName);
            user.setName(name);
            user.setRoles(user.getRoles());
            user.setPassword(user.getPassword());
            user.setAddress(address);
            user.setEmail(email);
            user.setSurname(surname);
            user.setNumber(number);
            user.setId(id);
            return this.userRepository.save(user);
        }else {
          throw new UserAlreadyExists();
        }





    }

    @Override
    public long findAnotherSameUserName(String userName,Long id) {
        return this.userRepository.findAnotherSameUserName(userName,id);
    }

    @Override
    public User editUserImg(Long id, String userName, String name, String surname, String address, String number, String email, byte[] file) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);

        if((this.userRepository.findAnotherSameUserName(userName,id))==null){
            user.setUserName(userName);
            user.setName(name);
            user.setRoles(user.getRoles());
            user.setPassword(user.getPassword());
            user.setAddress(address);
            user.setEmail(email);
            user.setSurname(surname);
            user.setNumber(number);
            user.setId(id);
            if(file!=null){
                user.setFile(file);
            }else {
                user.setFile(user.getFile());
            }

            return this.userRepository.save(user);
        }else {
            throw new UserAlreadyExists();
        }




    }

    @Override
    public List<Book> allFavouriteBooksOfUser(Long id) {
        return this.userRepository.allFavouriteBooksOfUser(id);
    }

    @Override
    public void deleteFavBook(Long id, Book book) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);
        List<Book>allBooksLiked=user.getLikedBooks();
        if(allBooksLiked.contains(book)){
            allBooksLiked.remove(book);
        }
        user.setLikedBooks(allBooksLiked);
        this.userRepository.save(user);

    }

    @Override
    public User addFavouriteBook(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public List<Book> allBooksOrdered(Long id) {
        return this.userRepository.allBooksOrdered(id);
    }

    @Override
    public void deleteOrderedBook(Long id, Book book) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);
        List<Book>allBooksOrdered=user.getOrderedBooks();
        allBooksOrdered.remove(book);
        user.setOrderedBooks(allBooksOrdered);
        this.userRepository.save(user);
    }

    @Override
    public List<Book> getAllRequestsOrders() {
        return this.userRepository.getAllRequestsOrders();
    }

    @Override
    public userOrdered addFavouriteBookStatus(User user, Book book) {

        userOrdered userOrdered=new userOrdered();
        userOrdered.setId(new userOrderedBooksKey(user.getId(),book.getName()));
        if(this.userOrderedBooks.findUserOrder(user,book)==null){
            userOrdered.setUser(user);
            userOrdered.setBook(book);
            userOrdered.setStatus(0);
            userOrdered.setIsInRequests(0);
            UserFavouriteBooks userFavouriteBooks=this.userFavouriteBooksRepository.findFavBookUser(user,book);
            userFavouriteBooks.setIsOrdered(1);
            this.userFavouriteBooksRepository.userFavouriteBookUpdate(userFavouriteBooks);
            return this.userOrderedBooks.userOrderedSave(userOrdered);
        }
       throw new UserFavouriteBooksAlreadyExists();
    }

    @Override
    public List<Book> getAllRequestsOrdersStatus() {
        return this.userOrderedBooks.getAllOrders();
    }

    @Override
    public List<Book> allBooksOrderedStatus(Long id) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);
        return this.userOrderedBooks.getAllBooksOrderedUser(user);
    }

    @Override
    public int getStatusBookOrdered(User user,Book name) {

        return this.userOrderedBooks.getStatusBookOrdered(user,name);
    }

    @Override
    public void deleteOrderedBookStatus(Long id, Book book) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);
        UserFavouriteBooks userFavouriteBooks=this.userFavouriteBooksRepository.findFavBookUser(user,book);
        userFavouriteBooks.setIsOrdered(0);
        this.userFavouriteBooksRepository.userFavouriteBookUpdate(userFavouriteBooks);
        this.userOrderedBooks.deleteOrder(user,book);
    }



    @Override
    public UserFavouriteBooks addFavouriteBookForUser(User user, Book book) {
        if(this.userFavouriteBooksRepository.findFavBookUser(user,book)==null){
            UserFavouriteBooks userFavouriteBooks=new UserFavouriteBooks();
            userFavouriteBooks.setId(new UserFavouriteBooksKey(user.getId(),book.getName()));
            userFavouriteBooks.setUser(user);
            userFavouriteBooks.setBook(book);

            userFavouriteBooks.setIsOrdered(0);
            return this.userFavouriteBooksRepository.userFavouriteBookSave(userFavouriteBooks);
        }
        throw new UserFavouriteBooksAlreadyExists();

    }

    @Override
    public void deleteFavouriteBookUser(Long id, Book book) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);
        this.userFavouriteBooksRepository.deleteFavouriteBook(user,book);
    }

    @Override
    public List<Book> getAllFavouriteBooksUser(Long id) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);
        return this.userFavouriteBooksRepository.getAllBooksFavouriteByUser(user);
    }

    @Override
    public int getStatusOrderedFavouriteBook(User user,Book book) {
        return this.userFavouriteBooksRepository.getStatusBookFavourite(user,book);
    }

    @Override
    public User getUserByBook(User user,Book book)
    {
        return this.userOrderedBooks.getUserByBook(user,book);
    }

    @Override
    public userOrdered approveOrder(User user, Book book) {
        userOrderedBooksKey key=new userOrderedBooksKey(user.getId(),book.getName());
//        userOrdered userOrdered=this.userOrderedBooks.findById(key).orElseThrow(InvalidBookOrderKye::new);
        userOrdered userOrdered=this.userOrderedBooks.findUserOrder(user,book);
        int i=0;
        userOrdered.setStatus(1);
        userOrdered.setIsInRequests(1);
        book.setAvailability(book.getAvailability()-1);
        this.bookRepository.save(book);
        return  this.userOrderedBooks.userOrderedSave(userOrdered);
    }

    @Override
    public userOrdered declineOrder(User user, Book book) {
        userOrderedBooksKey key=new userOrderedBooksKey(user.getId(),book.getName());
        userOrdered userOrdered=this.userOrderedBooks.findUserOrder(user,book);
        userOrdered.setStatus(2);
        userOrdered.setIsInRequests(1);
        return  this.userOrderedBooks.userOrderedSave(userOrdered);
    }

    @Override
    public List<userOrdered> getAllRequests() {
        return this.userOrderedBooks.getAllRequests();
    }

    @Override
    public Page<userOrdered> getAllRequestsPaginate(int page, int size) {
        List<userOrdered>userOrderedList=this.userOrderedBooks.getAllRequests();
        return Page.slice(userOrderedList,page,size);
    }

    @Override
    public Page<Book> allOrderedBooksStatus(int page, int size, Long id) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);
        List<Book>userOrderedList=this.userOrderedBooks.getAllBooksOrderedUser(user);
        return Page.slice(userOrderedList,page,size);
    }

    @Override
    public Page<Book> getAllFavouriteBooksUserPaginate(int page, int size, Long id) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);
        List<Book>userFavouriteList=this.userFavouriteBooksRepository.getAllBooksFavouriteByUser(user);

        return Page.slice(userFavouriteList,page,size);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUserName(s);
    }



}
