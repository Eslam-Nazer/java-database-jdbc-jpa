package com.devtrio.JavaRest.controllers;

import com.devtrio.JavaRest.domain.entities.Author;
import com.devtrio.JavaRest.domain.entities.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @GetMapping(path = "/books")
    public Book index() {
        return Book.builder()
                .isbn("987-0-12-47863-5")
                .title("The Enigma of Eternity")
                .author(new Author(null, "Owner name", 34))
                .yearPublished("2005")
                .build();
    }

    @PostMapping(path = "/books")
    public Book store(@RequestBody final Book book) {
        return book;
    }
}
