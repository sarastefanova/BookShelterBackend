package com.example.books.service.impl;

import com.example.books.model.Author;
import com.example.books.model.Book;
import com.example.books.model.exceptions.BookAlreadyExists;
import com.example.books.model.exceptions.InvalidAuthorsId;
import com.example.books.model.exceptions.InvalidAuthorsName;
import com.example.books.model.exceptions.InvalidBookId;
import com.example.books.model.paginate.Page;
import com.example.books.repository.AuthorRepository;
import com.example.books.repository.BookRepository;
import com.example.books.service.BookService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
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
                return this.bookRepository.save(book);
            }
              else throw new BookAlreadyExists();
    }

    @Override
    public void deleteBook(String name) {
        this.bookRepository.deleteById(name);
    }

    @Override
    public Page<Book> getAllBooks(int page, int size) {
        Page<Book>newBooks =this.bookRepository.getAllBooks(page,size);
        Long i=newBooks.getContent().stream().filter(p->p.getAuthor().getIsDeleted()==1).count();


        return this.bookRepository.getAllBooks(page,size);
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

}
