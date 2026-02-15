package com.devtrio.BooksRestApi.services;

import com.devtrio.BooksRestApi.domain.entities.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Author create(Author author);

    List<Author> findAll();

    Optional<Author> findById(long id);
}
