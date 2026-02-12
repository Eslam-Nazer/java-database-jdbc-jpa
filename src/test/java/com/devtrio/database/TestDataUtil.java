package com.devtrio.database;

import com.devtrio.database.domain.Author;
import com.devtrio.database.domain.Book;
import com.devtrio.database.repositories.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

public class TestDataUtil {

    public static Author createTestAuthor() {
        return Author.builder()
                .name("Abigail Rose")
                .age(25)
                .build();
    }

    public static List<Author> createTestAuthors() {
        List<Author> authors = new ArrayList<Author>();

        authors.add(Author.builder().name("John Doe").age(38).build());
        authors.add(Author.builder().name("Dara Tom").age(28).build());
        authors.add(Author.builder().name("Sara Don").age(24).build());

        return authors;
    }

    public static Book createTestBook(Author author) {

        return Book.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .author(author)
                .build();
    }

    public static List<Book> createTestBooks() {
        List<Book> books = new ArrayList<Book>();

        Author author = createTestAuthor();

        books.add(Book.builder()
                .isbn("978-1-2345-6789-1")
                .title("The Shadow in the Attic")
                .author(author)
                .build()
        );

        books.add(Book.builder()
                .isbn("978-1-2345-6789-2")
                .title("Beyond the Horizon")
                .author(author)
                .build()
        );

        books.add(Book.builder()
                .isbn("978-1-2345-6789-3")
                .title("The Last Ember")
                .author(author)
                .build()
        );

        return books;
    }
}
