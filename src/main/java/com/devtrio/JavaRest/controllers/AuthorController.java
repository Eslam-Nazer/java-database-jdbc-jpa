package com.devtrio.database.controllers;

import com.devtrio.database.domain.dto.AuthorDto;
import com.devtrio.database.services.AuthorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping(path = "/authors")
    public AuthorDto create(@RequestBody AuthorDto author) {
        return authorService.create(author);
    }
}
