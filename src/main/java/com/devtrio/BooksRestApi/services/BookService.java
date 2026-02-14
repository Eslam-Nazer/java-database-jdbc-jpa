package com.devtrio.BooksRestApi.services;

import com.devtrio.BooksRestApi.domain.entities.Book;

public interface BookService {

    Book create(String isbn, Book book);
}
