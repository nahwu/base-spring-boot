package com.nahwu.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
//@EqualsAndHashCode
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

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAuthorKey that = (BookAuthorKey) o;
        return isbn.equals(that.isbn) && authorId.equals(that.authorId);
    }
    */

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAuthorKey that = (BookAuthorKey) o;
        if (isbn == null){
            if (that.isbn != null){
                return false;
            }
        } else if (!isbn.equals(that.isbn)){
            return false;
        }
        if (authorId == null){
            if (that.authorId != null){
                return false;
            }
        } else if (!authorId.equals(that.authorId)){
            return false;
        }
        return true;
    }
*/
    @Override
    public int hashCode() {
        return Objects.hash(isbn, authorId);
    }

}
