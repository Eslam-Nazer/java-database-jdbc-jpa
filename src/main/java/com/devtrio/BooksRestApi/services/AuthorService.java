package com.devtrio.BooksRestApi.services;

import com.devtrio.BooksRestApi.domain.entities.Author;

import java.util.List;

public interface AuthorService {

    Author create(Author author);

    List<Author> findAll();
}
