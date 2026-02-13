package com.devtrio.JavaRest.controllers;

import com.devtrio.JavaRest.domain.dto.AuthorDto;
import com.devtrio.JavaRest.domain.entities.Author;
import com.devtrio.JavaRest.mappers.Mapper;
import com.devtrio.JavaRest.mappers.implemention.AuthorMapperImpl;
import com.devtrio.JavaRest.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private final AuthorService authorService;
    private Mapper<Author, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<Author, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> create(@RequestBody AuthorDto authorDto) {
        Author author = authorMapper.mapFrom(authorDto);
        author = authorService.create(author);

        return new ResponseEntity<>(authorMapper.mapTo(author), HttpStatus.CREATED);
    }
}
