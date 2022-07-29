package com.nahwu.base.controller;

import com.nahwu.base.entity.Book;
import com.nahwu.base.entity.BookDTO;
import com.nahwu.base.service.MiscService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private MiscService miscService;

    @PostMapping("/v1/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(
            @RequestBody final Book book) {
        return miscService.addBook(book);
    }

    @PutMapping("/v1/books")
    @ResponseStatus(HttpStatus.OK)
    public Book updateBook(
            @RequestBody final BookDTO editedBook) {
        return miscService.updateBook(editedBook);
    }

    @GetMapping("/v1/books/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> findByTitle(@PathVariable String title) {
        return miscService.findByTitle(title);
    }

    @GetMapping("/v1/books/author/{author}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> findByAuthor(@PathVariable String author) {
        return miscService.findByAuthors(author);
    }

    @DeleteMapping("/v1/books/isbn/{isbn}")
    public void deleteByIsbn(@PathVariable String isbn) {
         miscService.deleteByIsbn(isbn);
    }

    @GetMapping("/v1/books")
    public Page<Book> getAllBooks(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "pageNumber", required = false) @Valid @Min(1) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) @Valid @Min(1) Integer pageSize,
            @RequestParam(value = "orderBy", required = false) @Parameter(name = "Default: TITLE") Book.OrderBy orderByRequest,
            @RequestParam(value = "orderByDirection", required = false) @Parameter(name = "Default: DESC") Sort.Direction orderByDirection) {
        return miscService.getPagedListOfBooks(isbn, title, author, pageNumber, pageSize, orderByRequest, orderByDirection);
    }

}
