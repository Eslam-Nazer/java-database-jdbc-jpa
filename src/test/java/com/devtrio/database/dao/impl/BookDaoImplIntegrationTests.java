package com.devtrio.database.dao.impl;

import com.devtrio.database.TestDataUtil;
import com.devtrio.database.dao.AuthorDao;
import com.devtrio.database.dao.Impl.BookDaoImpl;
import com.devtrio.database.domain.Author;
import com.devtrio.database.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTests {

    private final AuthorDao authorDao;
    private final BookDaoImpl underTest;

    @Autowired
    public BookDaoImplIntegrationTests(BookDaoImpl underTest, AuthorDao authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBook();
        book.setAuthorId(author.getId());

        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatBooksCanBeCreatedAndRecalled() {
        List<Book> books = TestDataUtil.createTestBooks(authorDao);

        for (Book book: books) {
            underTest.create(book);
        }

        List<Book> results = underTest.find();

        Assertions.assertThat(results)
                .hasSize(3)
                .containsExactlyElementsOf(books)
                .containsExactlyInAnyOrderElementsOf(books);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBook();
        String isbn = book.getIsbn();
        underTest.create(book);

        book.setTitle("Updated This Title");
        underTest.update(isbn, book);

        Optional<Book> result = underTest.findOne(isbn);

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatBookCanBe() {
        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBook();
        book.setAuthorId(author.getId());
        String isbn = book.getIsbn();
        underTest.create(book);

        underTest.delete(isbn);
        
        Optional<Book> result = underTest.findOne(isbn);

        Assertions.assertThat(result).isEmpty();
    }
}
