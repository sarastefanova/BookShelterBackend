package com.example.books.repository.jpa;

import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.model.UserAllBooksWithFav;
import com.example.books.model.UserAllBooksWithFavKey;
import com.example.books.model.paginate.Page;
import com.example.books.repository.UserAllBooksWithFavRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserAllBooksWithFavRepositoryImpl implements UserAllBooksWithFavRepository {

    private final UserAllBooksWithFavJpaRepository userAllBooksWithFavJpaRepository;

    public UserAllBooksWithFavRepositoryImpl(UserAllBooksWithFavJpaRepository userAllBooksWithFavJpaRepository) {
        this.userAllBooksWithFavJpaRepository = userAllBooksWithFavJpaRepository;
    }

    @Override
    public List<UserAllBooksWithFav> getAllBooks() {
        return this.userAllBooksWithFavJpaRepository.findAllBooks();
    }

    @Override
    public Optional<UserAllBooksWithFav> findById(User user,Book book) {
        return this.userAllBooksWithFavJpaRepository.findByIdAllBooksFav(user,book);
    }

    @Override
    public UserAllBooksWithFav save(UserAllBooksWithFav UserAllBooksWithFav) {
        return this.userAllBooksWithFavJpaRepository.save(UserAllBooksWithFav);
    }

    @Override
    public void saveAllBooks(User user, List<Book> getAllBooks) {
        for (Book b:getAllBooks) {
            UserAllBooksWithFavKey allBooksWithFavKey=new UserAllBooksWithFavKey(user.getId(),b.getName());
            UserAllBooksWithFav userAllBooksWithFav=new UserAllBooksWithFav();
            userAllBooksWithFav.setId(allBooksWithFavKey);
            userAllBooksWithFav.setUser(user);
            userAllBooksWithFav.setBook(b);
            this.userAllBooksWithFavJpaRepository.save(userAllBooksWithFav);
        }
    }

    @Override
    public List<UserAllBooksWithFav> getAllBooksWithFavUser(User user) {

        return this.userAllBooksWithFavJpaRepository.getAllBooksWithFavUser(user);
    }

    @Override
    public User findUser(User user) {
        return this.userAllBooksWithFavJpaRepository.findUser(user);
    }

    @Override
    public List<UserAllBooksWithFav> searchBookOrAuthor(List<Book> books, User user) {
        List<UserAllBooksWithFav>allBooksWithFavs=new ArrayList<>();
        for (Book b : books) {
            UserAllBooksWithFav userAllBooksWithFav=this.userAllBooksWithFavJpaRepository.searchBookOrAuthor2(b,user);
            allBooksWithFavs.add(userAllBooksWithFav);
        }
        return  allBooksWithFavs;
    }

    @Override
    public void saveBookForEachUser(Book book) {
        List<User> listAllUserFavBooks=this.userAllBooksWithFavJpaRepository.listAllUserFavBooks();

        for (User u:listAllUserFavBooks) {
            UserAllBooksWithFavKey userAllBooksWithFavKey=new UserAllBooksWithFavKey(u.getId(),book.getName());
            UserAllBooksWithFav userAllBooksWithFav=new UserAllBooksWithFav();
            userAllBooksWithFav.setId(userAllBooksWithFavKey);
            userAllBooksWithFav.setBook(book);
            userAllBooksWithFav.setUser(u);
            this.userAllBooksWithFavJpaRepository.save(userAllBooksWithFav);
        }
    }
}
