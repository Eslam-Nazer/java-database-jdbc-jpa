package com.devtrio.BooksRestApi.services;

import com.devtrio.BooksRestApi.domain.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book create(String isbn, Book book);

    List<Book> findAll();

    Optional<Book> findById(String isbn);
}
