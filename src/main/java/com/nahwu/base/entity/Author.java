package com.nahwu.base.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "author")
@NoArgsConstructor
public class Author {

    // For mapping with BookAuthors
    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private Set<BookAuthors> books = new HashSet<>();

    // For creating books
    @JsonIgnore
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books2;

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "author_id", updatable = false, nullable = false)
    private String authorId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthday")
    private Date birthday;

    @JsonCreator
    public Author(String authorId, String name, Date birthday) {
        this.authorId = authorId;
        this.name = name;
        this.birthday = birthday;
    }
}


