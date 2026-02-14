package com.devtrio.BooksRestApi.repositories;

import com.devtrio.BooksRestApi.domain.entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
}
