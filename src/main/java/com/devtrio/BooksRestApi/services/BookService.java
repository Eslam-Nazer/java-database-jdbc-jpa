package com.devtrio.BooksRestApi.services;

import com.devtrio.BooksRestApi.domain.entities.Book;

import java.util.List;

public interface BookService {

    Book create(String isbn, Book book);

    List<Book> findAll();
}
