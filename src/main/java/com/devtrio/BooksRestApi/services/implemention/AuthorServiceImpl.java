package com.devtrio.BooksRestApi.services.implemention;

import com.devtrio.BooksRestApi.domain.entities.Author;
import com.devtrio.BooksRestApi.repositories.AuthorRepository;
import com.devtrio.BooksRestApi.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author create(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> findAll() {
        Spliterator<Author> spliterator = authorRepository.findAll().spliterator();
        return StreamSupport.stream(spliterator, false).collect(Collectors.toList());
    }
}
