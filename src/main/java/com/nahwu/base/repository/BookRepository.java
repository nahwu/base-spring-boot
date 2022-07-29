package com.nahwu.base.repository;

import com.nahwu.base.entity.Book;
import com.nahwu.base.entity.BookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//@Repository
public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {

    @Query(value = "SELECT new com.nahwu.base.entity.BookDTO(isbn, title, year, price)  " +
            " FROM Book " +
            " WHERE title = ?1 ")
    List<BookDTO> findByTitle(String title);

    /*
    @Query(value = " SELECT new com.nahwu.base.entity.BookDTO(isbn)" +
            " FROM Book " +
            " WHERE isbn = ?1 ")
    BookDTO findByIsbn(String isbn);
*/
    Book findByIsbn(String isbn);

    @Modifying
    Book deleteByIsbn(String isbn);
}
