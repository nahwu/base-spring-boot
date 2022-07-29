package com.nahwu.base.service;

import com.nahwu.base.entity.Book;
import com.nahwu.base.entity.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.Min;

public interface MiscService {

    ResponseEntity<?> addBook(Book newBook);

    ResponseEntity<?> updateBook(BookDTO updatedBook);

    ResponseEntity<?> findByTitle(String title);

    ResponseEntity<?> findByAuthors(String author);

    ResponseEntity<?> deleteByIsbn(String isbn);

}
