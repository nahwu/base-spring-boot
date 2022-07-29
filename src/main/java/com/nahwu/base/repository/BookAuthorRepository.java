package com.nahwu.base.repository;

import com.nahwu.base.entity.Author;
import com.nahwu.base.entity.BookAuthors;
import com.nahwu.base.entity.BookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BookAuthorRepository  extends JpaRepository<BookAuthors, String>, JpaSpecificationExecutor<BookAuthors>  {

    @Query(value = "SELECT new com.nahwu.base.entity.Author(a.authorId, a.name, a.birthday) " +
            " FROM BookAuthors ba  " +
            " LEFT JOIN Author a " +
            " ON a.authorId = ba.author.authorId " +
            " WHERE ba.book.isbn = :isbn ")
    List<Author> findAllAuthorsByIsbn(String isbn);

    @Query(value = "SELECT new com.nahwu.base.entity.BookDTO(b.isbn, b.title, b.year, b.price) " +
            " FROM BookAuthors ba  " +
            " LEFT JOIN Author a " +
            " ON a.authorId = ba.author.authorId " +
            " LEFT JOIN Book b " +
            " ON b.isbn = ba.book.isbn " +
            " WHERE a.name = :authorName ")
    List<BookDTO> findAuthorBooksByAuthorName(String authorName);



}
