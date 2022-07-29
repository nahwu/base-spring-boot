package com.nahwu.base.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@Embeddable
public class BookAuthorKey implements Serializable {

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "author_id")
    private String authorId;

    public BookAuthorKey(String isbn, String authorId) {
        this.isbn = isbn;
        this.authorId = authorId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, authorId);
    }

}
