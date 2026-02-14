package com.devtrio.BooksRestApi.repositories;

import com.devtrio.BooksRestApi.TestDataUtil;
import com.devtrio.BooksRestApi.domain.entities.Author;
import com.devtrio.BooksRestApi.domain.entities.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

    private final BookRepository underTest;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthor();

        Book book = TestDataUtil.createTestBook(author);

        book = underTest.save(book);
        Optional<Book> result = underTest.findById(book.getIsbn());

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatBooksCanBeCreatedAndRecalled() {
        List<Book> books = TestDataUtil.createTestBooks()
                .stream()
                .map(underTest::save)
                .collect(Collectors.toList());

        Iterable<Book> results = underTest.findAll();

        Assertions.assertThat(results)
                .hasSize(3)
                .containsExactlyElementsOf(books)
                .containsExactlyInAnyOrderElementsOf(books);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthor();

        Book book = TestDataUtil.createTestBook(author);
        String isbn = book.getIsbn();
       book = underTest.save(book);

        book.setTitle("Updated This Title");
        underTest.save(book);

        Optional<Book> result = underTest.findById(isbn);

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatBookCanBe() {
        Author author = TestDataUtil.createTestAuthor();

        Book book = TestDataUtil.createTestBook(author);

        String isbn = book.getIsbn();
        underTest.save(book);

        underTest.deleteById(isbn);

        Optional<Book> result = underTest.findById(isbn);

        Assertions.assertThat(result).isEmpty();
    }
}
