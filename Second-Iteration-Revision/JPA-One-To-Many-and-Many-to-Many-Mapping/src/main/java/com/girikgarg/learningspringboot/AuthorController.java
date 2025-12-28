package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    
    @Autowired
    private AuthorService authorService;
    
    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }
    
    @GetMapping("/{id}")
    public Author getAuthor(@PathVariable Long id) {
        // In bidirectional mapping, this will return author with all their books!
        return authorService.getAuthorWithBooks(id);
    }
    
    // Dedicated endpoint to get author's books
    @GetMapping("/{id}/books")
    public Author getAuthorWithBooks(@PathVariable Long id) {
        return authorService.getAuthorWithBooks(id);
    }
    
    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }
}

