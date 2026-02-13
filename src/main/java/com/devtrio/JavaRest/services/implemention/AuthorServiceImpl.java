package com.devtrio.JavaRest.services.implemention;

import com.devtrio.JavaRest.domain.entities.Author;
import com.devtrio.JavaRest.repositories.AuthorRepository;
import com.devtrio.JavaRest.services.AuthorService;
import org.springframework.stereotype.Service;

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
}
