package com.nahwu.base.service;

import com.nahwu.base.entity.Book;
import com.nahwu.base.entity.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;
import java.util.List;

public interface MiscService {

    Book addBook(Book newBook);
    Book updateBook(BookDTO updatedBook);

    List<BookDTO> findByTitle(String title);

    List<BookDTO> findByAuthors(String author);

    void deleteByIsbn(String isbn);

    Page<Book> getPagedListOfBooks(String isbn, String title, String author, @Min(1) Integer pageNumber, @Min(1) Integer pageSize, Book.OrderBy orderByRequest, Sort.Direction orderByDirection);



}
