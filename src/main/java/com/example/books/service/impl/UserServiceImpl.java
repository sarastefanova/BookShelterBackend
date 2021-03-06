package com.example.books.service.impl;

import com.example.books.model.*;
import com.example.books.model.exceptions.*;
import com.example.books.model.paginate.Page;
import com.example.books.repository.*;
import com.example.books.repository.jpa.UserAllBooksWithFavJpaRepository;
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
    private final UserAllBooksWithFavRepository userAllBooksWithFavRepository;
    private final UserOrderedBooks userOrderedBooks;
    private final UserFavouriteBooksRepository userFavouriteBooksRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BookRepository bookRepository, UserAllBooksWithFavRepository userAllBooksWithFavRepository, UserOrderedBooks userOrderedBooks, UserAllBooksWithFavJpaRepository userAllBooksWithFavJpaRepository, UserFavouriteBooksRepository userFavouriteBooksRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.userAllBooksWithFavRepository = userAllBooksWithFavRepository;
        this.userOrderedBooks = userOrderedBooks;
        this.userFavouriteBooksRepository = userFavouriteBooksRepository;
    }

    @Override
    public User getByUserName(String userName) {
        return this.userRepository.findByUserName(userName);
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

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
    public User editUser(Long id, String userName, String name, String surname, String address, String number, String email, byte[] file) {
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
    public userOrdered addOrderedBookUser(User user, Book book) {
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
    public int getStatusBookOrdered(User user,Book name) {

        return this.userOrderedBooks.getStatusBookOrdered(user,name);
    }

    @Override
    public void deleteOrderedBook(Long id, Book book) {
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
            UserAllBooksWithFav userAllBooksWithFav=this.userAllBooksWithFavRepository.findById(user,book).orElseThrow(InvalidAuthorsId::new);
            userAllBooksWithFav.setInFavourite(1);
            this.userAllBooksWithFavRepository.save(userAllBooksWithFav);
            return this.userFavouriteBooksRepository.userFavouriteBookSave(userFavouriteBooks);
        }
        throw new UserFavouriteBooksAlreadyExists();

    }

    @Override
    public void deleteFavouriteBookUser(Long id, Book book) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);
        UserAllBooksWithFav userAllBooksWithFav=this.userAllBooksWithFavRepository.findById(user,book).orElseThrow(InvalidFavouriteBookId::new);
        userAllBooksWithFav.setInFavourite(0);
        userOrdered userOrderedBooks=this.userOrderedBooks.findUserOrder(user,book);
        if(userOrderedBooks!=null){
            this.userOrderedBooks.deleteOrder(user,book);
        }
        this.userAllBooksWithFavRepository.save(userAllBooksWithFav);
        this.userFavouriteBooksRepository.deleteFavouriteBook(user,book);
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
        userOrdered userOrdered=this.userOrderedBooks.findUserOrder(user,book);
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
    public Page<userOrdered> getAllRequestsPaginate(int page, int size) {
        List<userOrdered>userOrderedList=this.userOrderedBooks.getAllRequests();
        return Page.slice(userOrderedList,page,size);
    }

    @Override
    public Page<Book> allOrderedBooks(int page, int size, Long id) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);
        List<Book>userOrderedList=this.userOrderedBooks.getAllBooksOrderedUser(user);
        return Page.slice(userOrderedList,page,size);
    }

    @Override
    public Page<UserFavouriteBooks> getFavouriteBooksUserPaginate(int page, int size, Long id) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);
        List<UserFavouriteBooks>userFavouriteList=this.userFavouriteBooksRepository.listFavoriteBooksUser(user);

        return Page.slice(userFavouriteList,page,size);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUserName(s);
    }



}
