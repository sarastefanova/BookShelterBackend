package com.example.books.repository.jpa;

import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.model.UserFavouriteBooks;
import com.example.books.model.UserFavouriteBooksKey;
import com.example.books.model.exceptions.InvalidFavouriteBookId;
import com.example.books.model.exceptions.UserFavouriteBooksAlreadyExists;
import com.example.books.repository.UserFavouriteBooksRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserFavouriteBooksRepositoryImpl  implements UserFavouriteBooksRepository {

    private final UserFavouriteBooksRepositoryJpa userFavouriteBooksRepositoryJpa;

    public UserFavouriteBooksRepositoryImpl(UserFavouriteBooksRepositoryJpa userFavouriteBooksRepositoryJpa) {
        this.userFavouriteBooksRepositoryJpa = userFavouriteBooksRepositoryJpa;
    }

    @Override
    public UserFavouriteBooks userFavouriteBookSave(UserFavouriteBooks userFavouriteBooks) {
        UserFavouriteBooks findUserFavBook=this.userFavouriteBooksRepositoryJpa.findFavBookUser(userFavouriteBooks.getUser(),userFavouriteBooks.getBook());
       if(findUserFavBook==null){
            return this.userFavouriteBooksRepositoryJpa.save(userFavouriteBooks);
        }

        throw new InvalidFavouriteBookId();
    }

    @Override
    public Optional<UserFavouriteBooks> findById(UserFavouriteBooksKey userFavouriteBooksKey) {
        return this.userFavouriteBooksRepositoryJpa.findById(userFavouriteBooksKey);
    }

    @Override
    public int getStatusBookFavourite(User user,Book name) {
        int pom=this.userFavouriteBooksRepositoryJpa.getStatusBookFavourite(user,name);
        return this.userFavouriteBooksRepositoryJpa.getStatusBookFavourite(user,name);
    }

    @Override
    public UserFavouriteBooks userFavouriteBookUpdate(UserFavouriteBooks userFavouriteBooks) {
        UserFavouriteBooks userFavourite=this.userFavouriteBooksRepositoryJpa.findByIdBook(userFavouriteBooks.getUser(),userFavouriteBooks.getBook());
        int i=0;
        return this.userFavouriteBooksRepositoryJpa.save(userFavourite);
    }

    @Override
    public void deleteFavouriteBook(User user, Book book) {
            UserFavouriteBooks userFavouriteBooks=this.userFavouriteBooksRepositoryJpa.findByIdBook(user,book);
            this.userFavouriteBooksRepositoryJpa.delete(userFavouriteBooks);

    }

    @Override
    public List<User> getAllUsersFavouriteBooks() {
        List<User>bla=this.userFavouriteBooksRepositoryJpa.getAllUsersFavouriteBooks();
        return this.userFavouriteBooksRepositoryJpa.getAllUsersFavouriteBooks();
    }

    @Override
    public List<UserFavouriteBooks> listFavoriteBooksUser(User user) {
        return this.userFavouriteBooksRepositoryJpa.listFavoriteBooksUser(user);
    }

    @Override
    public UserFavouriteBooks findFavBookUser(User user, Book book) {
        return this.userFavouriteBooksRepositoryJpa.findFavBookUser(user,book);
    }
}
