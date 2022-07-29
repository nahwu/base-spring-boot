package com.nahwu.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "book_authors")
public class BookAuthors {

    @JsonIgnore
    @EmbeddedId
    private BookAuthorKey id;

    @JsonIgnore
    @ManyToOne
    @MapsId("isbn")
    @JoinColumn(name = "isbn")
    private Book book;

    @JsonIgnore
    @ManyToOne
    @MapsId("authorId")
    @JoinColumn(name = "author_id")
    private Author author;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
