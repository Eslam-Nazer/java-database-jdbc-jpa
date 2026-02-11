package com.devtrio.database.repositories;

import com.devtrio.database.TestDataUtil;
import com.devtrio.database.dao.Impl.AuthorDaoImpl;
import com.devtrio.database.domain.Author;
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
public class AuthorDaoImplIntegrationTests {

    private final AuthorDaoImpl underTest;

    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatFindOneGeneratesCorrectSql() {
        Author author = TestDataUtil.createTestAuthor();

        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        List<Author> authors = TestDataUtil.createTestAuthors();
        for(Author author: authors) {
            underTest.create(author);
        }

        List<Author> results = underTest.find();

        Assertions.assertThat(results)
                .hasSize(3)
                .containsExactlyElementsOf(authors)
                .containsExactlyInAnyOrderElementsOf(authors);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);

        author.setName("Updated Name");
        author.setAge(40);
        underTest.update(author.getId(), author);

        Optional<Author> result = underTest.findOne(author.getId());

        Assertions.assertThat(result)
                .isPresent();
        Assertions.assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);

        underTest.delete(author.getId());

        Optional<Author> result = underTest.findOne(author.getId());

        Assertions.assertThat(result).isEmpty();
    }
}
