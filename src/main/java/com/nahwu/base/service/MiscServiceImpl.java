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

    @Override
    public Page<Book> getPagedListOfBooks(String isbn, String title, String author, @Min(1) Integer pageNumber, @Min(1) Integer pageSize, Book.OrderBy orderByRequest, Sort.Direction orderByDirection) {
        final int PAGE_NUMBER_DEFAULT = 1;
        final int PAGE_SIZE_DEFAULT = Integer.MAX_VALUE;
        String orderByValue;
        final String ORDER_BY_VALUE_DEFAULT = Book.OrderBy.TITLE.getFieldName();
        final Sort.Direction ORDER_BY_DIRECTION_DEFAULT = Sort.Direction.DESC;

        // Check and set default values if not provided
        if (pageNumber == null) {
            pageNumber = PAGE_NUMBER_DEFAULT;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE_DEFAULT;
        }
        if (orderByRequest == null) {
            orderByValue = ORDER_BY_VALUE_DEFAULT;
        } else {
            orderByValue = orderByRequest.getFieldName();
        }
        if (orderByDirection == null) {
            orderByDirection = ORDER_BY_DIRECTION_DEFAULT;
        }

        // Build the query specification.
        LinkedList<Specification<Book>> specs = new LinkedList<>();
        if (isbn != null) {
            specs.add(this.hasTitle(isbn));
        }
        if (isbn != null) {
            specs.add(this.hasTitle(isbn));
        }

        Sort.Order primarySortOrder = new Sort.Order(orderByDirection, orderByValue);
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, Sort.by(primarySortOrder));

        Page<Book> pagedResults;
        if (specs.size() > 0) {
            Specification<Book> spec = this.combineSpecifications(specs);

            pagedResults = bookRepository.findAll(spec, pageRequest);
        } else {
            pagedResults = bookRepository.findAll(pageRequest);
        }

        return pagedResults;
    }

    private Specification<Book> hasTitle(String bookTitle) {
        return (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(criteriaBuilder.lower(root.get(Book.OrderBy.TITLE.getFieldName())), bookTitle.toLowerCase());
    }

    private Specification<Book> combineSpecifications(LinkedList<Specification<Book>> specs) {
        Specification<Book> spec = null;
        if (specs.size() > 0) {
            spec = Specification.where(specs.removeFirst());
            for (Specification<Book> s : specs) {
                spec = spec.and(s);
            }
        }
        return spec;
    }
}
