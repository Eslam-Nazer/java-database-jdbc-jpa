package com.devtrio.BooksRestApi.controllers;

import com.devtrio.BooksRestApi.domain.dto.BookDto;
import com.devtrio.BooksRestApi.domain.entities.Book;
import com.devtrio.BooksRestApi.mappers.Mapper;
import com.devtrio.BooksRestApi.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final Mapper<Book, BookDto> bookMapper;
    private final BookService bookService;

    public BookController(Mapper<Book, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @GetMapping(path = "/books")
    public ResponseEntity<List<BookDto>> index() {
        List<Book> books = bookService.findAll();
        List<BookDto> bookDtos = books.stream().map(bookMapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> save(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
        Book book = bookMapper.mapFrom(bookDto);
        boolean bookIsExists = bookService.isExists(isbn);
        book = bookService.save(isbn, book);
        bookDto = bookMapper.mapTo(book);

        if(bookIsExists) {
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
        }
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> show(@PathVariable("isbn") String isbn) {
        Optional<Book> foundBook =bookService.findById(isbn);

        return foundBook.map(book -> {
            BookDto bookDto = bookMapper.mapTo(book);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> patch(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
        if (! bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Book book = bookMapper.mapFrom(bookDto);
        Book patch = bookService.patch(isbn, book);
        bookDto = bookMapper.mapTo(patch);

        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }
}
