package com.devtrio.database;

import com.devtrio.database.domain.Author;
import com.devtrio.database.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class TestDataUtil {

    public static Author createTestAuthor() {
        return Author.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(25)
                .build();
    }

    public static List<Author> createTestAuthors() {
        List<Author> authors = new ArrayList<Author>();

        authors.add(Author.builder().id(1L).name("John Doe").age(38).build());
        authors.add(Author.builder().id(2L).name("Dara Tom").age(28).build());
        authors.add(Author.builder().id(3L).name("Sara Don").age(24).build());

        return authors;
    }

    public static Book createTestBook() {
        return Book.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .authorId(1L)
                .build();
    }
}
