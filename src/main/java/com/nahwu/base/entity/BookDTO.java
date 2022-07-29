package com.nahwu.base.entity;

import lombok.Data;

import java.util.List;

@Data
public class BookDTO {
    private String isbn;

    private String title;

    private List<Author> authors;

    private int year;

    private double price;

    public BookDTO(String isbn, String title, int year, double price) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.price = price;
    }

    public BookDTO(String isbn) {
        this.isbn = isbn;
    }
}
