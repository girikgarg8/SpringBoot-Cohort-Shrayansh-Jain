package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private AuthorRepository authorRepository;
    
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    
    @Transactional
    public Book createBookWithAuthors(String title, Double price, List<Long> authorIds) {
        Book book = new Book(title, price);
        
        // Fetch authors by IDs and add them to the book
        // In bidirectional relationship, we only need to set the OWNING side (Book)
        // The inverse side (Author.books) will be automatically populated by JPA
        for (Long authorId : authorIds) {
            Author author = authorRepository.findById(authorId).orElse(null);
            if (author != null) {
                book.addAuthor(author);
            }
        }
        
        return bookRepository.save(book);
    }
    
    @Transactional
    public Book addAuthorToBook(Long bookId, Long authorId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        Author author = authorRepository.findById(authorId).orElse(null);
        
        if (book != null && author != null) {
            book.addAuthor(author);
            return bookRepository.save(book);
        }
        
        return null;
    }
    
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}

