package com.nahwu.base.controller;

import com.nahwu.base.entity.Book;
import com.nahwu.base.entity.BookDTO;
import com.nahwu.base.service.MiscService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
public class BookController {

    @Autowired
    private MiscService miscService;

    @PostMapping("/v1/books")
    @Operation(summary = "Create a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the book",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Book.class)))}),
            @ApiResponse(responseCode = "409", description = "Book already exists with that ISBN",
                    content = @Content)})
    public ResponseEntity<?> createBook(
            @RequestBody final Book book) {
        return miscService.addBook(book);
    }

    @PutMapping("/v1/books")
    @Operation(summary = "Edit a book by its ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Book.class)))}),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)})
    public ResponseEntity<?> updateBook(
            @RequestBody final BookDTO editedBook) {
        return miscService.updateBook(editedBook);
    }

    @GetMapping("/v1/books/title/{title}")
    @Operation(summary = "Search a book by its title")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Book.class)))}),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)})
    public ResponseEntity<?> findByTitle(@Parameter(description = "Title of book to be searched") @PathVariable String title) {
        return miscService.findByTitle(title);
    }

    @GetMapping("/v1/books/author/{author}")
    @Operation(summary = "Search a book by its author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Book.class)))}),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)})
    public ResponseEntity<?> findByAuthor(@Parameter(description = "Author of book to be searched") @PathVariable String author) {
        return miscService.findByAuthors(author);
    }

    @DeleteMapping("/v1/books/isbn/{isbn}")
    @Operation(summary = "Delete a book by its ISBN. Requires admin rights.")
    public ResponseEntity<?> deleteByIsbn(@PathVariable String isbn) {
        return miscService.deleteByIsbn(isbn);
    }

}
