package com.nahwu.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
//@EqualsAndHashCode
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

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAuthors that = (BookAuthors) o;
        return id.equals(that.id);
    }
  */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
