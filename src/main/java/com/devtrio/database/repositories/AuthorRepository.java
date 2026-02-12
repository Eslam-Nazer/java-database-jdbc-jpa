package com.devtrio.database.repositories;

import com.devtrio.database.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    Iterable<Author> ageLessThan(int age);

    @Query("SELECT a from Author a WHERE a.age > ?1")
    Iterable<Author> findAuthorsWithGreaterThan(int age);
}
