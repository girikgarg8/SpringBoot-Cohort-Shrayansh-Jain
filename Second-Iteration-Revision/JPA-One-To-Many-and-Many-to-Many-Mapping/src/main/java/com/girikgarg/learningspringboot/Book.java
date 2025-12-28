package com.girikgarg.learningspringboot;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private Double price;
    
    // Many-to-Many BIDIRECTIONAL: Book knows about Authors, and Author knows about Books
    // Book is the OWNING side - it manages the relationship and has @JoinTable
    @ManyToMany
    @JoinTable(
        name = "book_authors",  // Join table name
        joinColumns = @JoinColumn(name = "book_id"),  // Foreign key for Book
        inverseJoinColumns = @JoinColumn(name = "author_id")  // Foreign key for Author
    )
    private List<Author> authors = new ArrayList<>();
    
    public Book() {
    }
    
    public Book(String title, Double price) {
        this.title = title;
        this.price = price;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public List<Author> getAuthors() {
        return authors;
    }
    
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
    
    // Helper method to add an author
    public void addAuthor(Author author) {
        this.authors.add(author);
    }
}

