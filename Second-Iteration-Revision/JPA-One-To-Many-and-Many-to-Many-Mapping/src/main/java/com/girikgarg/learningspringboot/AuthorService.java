package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    
    @Autowired
    private AuthorRepository authorRepository;
    
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }
    
    public Author getAuthor(Long id) {
        return authorRepository.findById(id).orElse(null);
    }
    
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    
    // Get author with their books (bidirectional)
    public Author getAuthorWithBooks(Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        // In bidirectional relationship, author.getBooks() will return all books by this author
        return author;
    }
}

