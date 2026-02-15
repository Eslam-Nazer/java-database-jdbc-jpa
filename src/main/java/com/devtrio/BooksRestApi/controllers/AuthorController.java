package com.devtrio.BooksRestApi.controllers;

import com.devtrio.BooksRestApi.domain.dto.AuthorDto;
import com.devtrio.BooksRestApi.domain.entities.Author;
import com.devtrio.BooksRestApi.mappers.Mapper;
import com.devtrio.BooksRestApi.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private final AuthorService authorService;
    private final Mapper<Author, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<Author, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @GetMapping(path = "/authors")
    public ResponseEntity<List<AuthorDto>> index() {

        List<Author> authors = authorService.findAll();
        List<AuthorDto> authorDtos = authors.stream().map(authorMapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(authorDtos, HttpStatus.OK);
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> create(@RequestBody AuthorDto authorDto) {
        Author author = authorMapper.mapFrom(authorDto);
        author = authorService.create(author);

        return new ResponseEntity<>(authorMapper.mapTo(author), HttpStatus.CREATED);
    }
}
