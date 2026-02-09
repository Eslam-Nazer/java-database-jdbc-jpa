package com.devtrio.database;

import com.devtrio.database.domain.Author;
import com.devtrio.database.domain.Book;

public class TestDataUtil {

    public static Author createTestAuthor() {
        return Author.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(25)
                .build();
    }

    public static Book createTestBook() {
        return Book.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .authorId(1L)
                .build();
    }
}
