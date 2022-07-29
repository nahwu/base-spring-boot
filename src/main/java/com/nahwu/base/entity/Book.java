package com.nahwu.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "isbn", updatable = false, nullable = false)
    private String isbn;

    @Column(name = "title")
    private String title;

    // For mapping with BookAuthors
    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private Set<BookAuthors> bookAuthorsMapping;


    // For creating books
    @ManyToMany
    @JoinTable(name = "book_authors",
            joinColumns = @JoinColumn(name = "isbn"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;


    @Column(name = "year")
    private int year;

    @Column(name = "price")
    private double price;

}
