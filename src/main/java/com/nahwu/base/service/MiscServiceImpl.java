package com.nahwu.base.service;

import com.nahwu.base.entity.*;
import com.nahwu.base.repository.AuthorRepository;
import com.nahwu.base.repository.BookAuthorRepository;
import com.nahwu.base.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import java.util.*;

@Service
public class MiscServiceImpl implements MiscService {
    private static final Logger logger = LoggerFactory.getLogger(MiscServiceImpl.class);

    @Autowired
    private Environment env;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookAuthorRepository bookAuthorRepository;

    @Override
    public ResponseEntity<?> addBook(Book newBook) {
        Book existingBook = bookRepository.findByIsbn(newBook.getIsbn());
        if (existingBook == null) {
            authorRepository.saveAll(newBook.getAuthors());
            return new ResponseEntity<>(bookRepository.save(newBook), HttpStatus.CREATED);
        } else {
            logger.warn("Unable to create Book due to existing book with the same ISBN.");
            return new ResponseEntity<>("Unable to create Book due to existing book with the same ISBN.", HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<?> updateBook(BookDTO requestedChangesBook) {
        Book existingBook = bookRepository.findByIsbn(requestedChangesBook.getIsbn());

        if (existingBook != null) {
            authorRepository.saveAll(requestedChangesBook.getAuthors());
            BeanUtils.copyProperties(requestedChangesBook, existingBook);
            existingBook.setAuthors(new HashSet<>(requestedChangesBook.getAuthors()));
            return new ResponseEntity<>(bookRepository.save(existingBook), HttpStatus.OK);
        } else {
            logger.warn("Unable to find Book with the provided ISBN.");
            return new ResponseEntity<>("Unable to find Book with the provided ISBN.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> findByTitle(String title) {
        List<BookDTO> retrievedBookList = bookRepository.findByTitle(title);
        if (retrievedBookList != null && !retrievedBookList.isEmpty()) {
            for (BookDTO bookItem : retrievedBookList) {
                bookItem.setAuthors(bookAuthorRepository.findAllAuthorsByIsbn(bookItem.getIsbn()));
            }
            return new ResponseEntity<>(retrievedBookList, HttpStatus.OK);
        } else {
            logger.warn("Unable to find Book with the provided title.");
            return new ResponseEntity<>("Unable to find Book with the provided title.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> findByAuthors(String authorName) {
        List<BookDTO> retrievedBookList = bookAuthorRepository.findAuthorBooksByAuthorName(authorName);

        if (retrievedBookList != null && !retrievedBookList.isEmpty()) {
            for (BookDTO bookItem : retrievedBookList) {
                bookItem.setAuthors(bookAuthorRepository.findAllAuthorsByIsbn(bookItem.getIsbn()));
            }
            return new ResponseEntity<>(retrievedBookList, HttpStatus.OK);
        } else {
            logger.warn("Unable to find Book with the provided title.");
            return new ResponseEntity<>("Unable to find Book with the provided author.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> deleteByIsbn(String isbn) {
        Book existingBook = bookRepository.findByIsbn(isbn);
        if (existingBook != null) {
            bookRepository.delete(existingBook);
            logger.info("Deleted book with ISDN: " + isbn);
            return new ResponseEntity<>("Deleted book with ISDN: " + isbn, HttpStatus.OK);
        } else {
            logger.warn("Unable to find Book with the provided ISBN.");
            return new ResponseEntity<>("Unable to find Book with the provided ISBN.", HttpStatus.NOT_FOUND);
        }
    }

}
