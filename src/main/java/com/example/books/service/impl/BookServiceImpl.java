package com.example.books.service.impl;

import com.example.books.model.*;
import com.example.books.model.exceptions.*;
import com.example.books.model.paginate.Page;
import com.example.books.repository.*;
import com.example.books.repository.jpa.BookJpaRepository;
import com.example.books.service.BookService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final UserFavouriteBooksRepository userFavouriteBooksRepository;
    private final UserAllBooksWithFavRepository userAllBooksWithFavRepository;
    private final BookJpaRepository bookJpaRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, UserRepository userRepository, UserFavouriteBooksRepository userFavouriteBooksRepository, UserAllBooksWithFavRepository userAllBooksWithFavRepository, BookJpaRepository bookJpaRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
        this.userFavouriteBooksRepository = userFavouriteBooksRepository;
        this.userAllBooksWithFavRepository = userAllBooksWithFavRepository;
        this.bookJpaRepository = bookJpaRepository;
    }

    @Override
    public List<Book> listBooks() {
        return this.bookRepository.getAllBooks();
    }

    @Override
    public Optional<Book> getById(String name) {
        return this.bookRepository.findById(name);
    }

    @Override
    public Book createBook(String name, String nameAndSurname, int price) throws InvalidAuthorsName{


            Author author=this.authorRepository.findById(nameAndSurname).orElseThrow(InvalidAuthorsId::new);

               Book book=new Book(name,author,price);

                return this.bookRepository.save(book);
    }

    @Override
    public Book createBookWithImg(String name, String nameAndSurname, int price, byte[] file,String shortContentBook,int availability) throws InvalidAuthorsName, IOException {

            Author author=this.authorRepository.findById(nameAndSurname).orElseThrow(InvalidAuthorsId::new);
            if((this.bookRepository.findAnotherSameUserName(name))==null){
                Book   book=new Book(name,author,price,file,shortContentBook,availability);
                book.setIsDeleted(0);
                return this.bookRepository.save(book);
            }
              else throw new BookAlreadyExists();
    }

    @Override
    public void deleteBook(String name) {
        this.bookRepository.deleteById(name);
    }

    @Override
    public Page<Book> getAllBooks(int page, int size,Long userId) {
        if(userId==0){
            Page<Book>newBooks =this.bookRepository.getAllBooks(page,size);
            Long i=newBooks.getContent().stream().filter(p->p.getAuthor().getIsDeleted()==1).count();


            return this.bookRepository.getAllBooks(page,size);
        }
        else{
           User user=this.userRepository.findById(userId).orElseThrow(InvalidUserId::new);
            User findUser=this.userAllBooksWithFavRepository.findUser(user);
            if(findUser==null){
                List<Book>getAllBooks=this.bookJpaRepository.findAllAuthors();
                this.userAllBooksWithFavRepository.saveAllBooks(user,getAllBooks);
            }

        }
        return null;
    }



    @Override
    public Book editBook(String name, String nameAndSurname, int price,String shortContentBook,int availability) throws InvalidBookId, InvalidAuthorsName {
        Book updateBook=  this.bookRepository.findById(name).orElseThrow(InvalidBookId::new);
        Author author=this.authorRepository.findById(nameAndSurname).orElseThrow(InvalidAuthorsId::new);

            updateBook.setAuthor(author);
            updateBook.setPrice(price);
            updateBook.setFile(updateBook.getFile());
            updateBook.setShortContentBook(shortContentBook);
            updateBook.setAvailability(availability);
            return this.bookRepository.save(updateBook);

    }

    @Override
    public List<Book> getAllBookByAuthor(String nameAndSurname) {
        return this.bookRepository.getAllBookByAuthor(nameAndSurname);
    }

    @Override
    public List<Book> getAllBooksAuthor() {
        return this.bookRepository.getAllBooksAuthor();
    }

    @Override
    public List<Book> searchBookOrAuthor(String name) {
        return this.bookRepository.searchBookOrAuthor(name);
    }

    @Override
    public Author getAuthorByBook(String name) {
        return this.bookRepository.getAuthorByBook(name);
    }

    @Override
    public Page<Book> searchBookOrAuthorPage(String name,int page, int pageSize) {
        List<Book>books=this.bookRepository.searchBookOrAuthor(name);
        return Page.slice(books,page,pageSize);
    }

    @Override
    public Page<UserFavouriteBooks> getAllBooksAuthorFavourite(int page, int size,Long id) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);
        return this.bookRepository.getAllBooksAuthorFavourite(page,size,user);
    }

    @Override
    public boolean checkIfUserHasThisBookFav(Long id, String name) {
        User user=this.userRepository.findById(id).orElseThrow(InvalidUserId::new);
        Book book=this.bookRepository.findById(name).orElseThrow(InvalidBookId::new);
        UserFavouriteBooks userFavouriteBooks=this.userFavouriteBooksRepository.findFavBookUser(user,book);
        if(userFavouriteBooks==null)
            return false;
        else
            return true;
    }

    @Override
    public List<Book> getNewestBooks() {
        return this.bookRepository.getNewestBooks();
    }

    @Override
    public Page<UserAllBooksWithFav> getAllBooksUserWithFav(int page, int size, Long id) {
        return this.bookRepository.getAllBooksUserWithFav(page,size,id);
    }

    @Override
    public int getInFavouritesBook(User user, Book book) {
        return this.userAllBooksWithFavRepository.getInFavouritesBook(user,book);
    }

}
