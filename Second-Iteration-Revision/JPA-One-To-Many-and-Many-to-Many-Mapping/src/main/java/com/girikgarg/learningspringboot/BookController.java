package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    // Create a book (can add authors later)
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }
    
    // Create a book with multiple authors (Many-to-Many)
    @PostMapping("/with-authors")
    public Book createBookWithAuthors(
            @RequestParam String title,
            @RequestParam Double price,
            @RequestParam List<Long> authorIds) {
        return bookService.createBookWithAuthors(title, price, authorIds);
    }
    
    // Add an author to an existing book
    @PostMapping("/{bookId}/authors/{authorId}")
    public Book addAuthorToBook(
            @PathVariable Long bookId,
            @PathVariable Long authorId) {
        return bookService.addAuthorToBook(bookId, authorId);
    }
    
    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        // This will return the book with all its authors (Many-to-Many)
        return bookService.getBook(id);
    }
    
    @GetMapping
    public List<Book> getAllBooks() {
        // This will return all books with their authors
        return bookService.getAllBooks();
    }
}


