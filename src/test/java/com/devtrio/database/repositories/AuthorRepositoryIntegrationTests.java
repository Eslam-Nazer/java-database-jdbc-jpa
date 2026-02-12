package com.devtrio.database.repositories;

import com.devtrio.database.TestDataUtil;
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
import java.util.stream.Collectors;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {

    private final AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthor();

        underTest.save(author);
        Optional<Author> result = underTest.findById(author.getId());

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        List<Author> authors = TestDataUtil.createTestAuthors()
                .stream()
                .map(underTest::save)
                .collect(Collectors.toList());

        Iterable<Author> results = underTest.findAll();

        Assertions.assertThat(results)
                .hasSize(3)
                .containsExactlyElementsOf(authors)
                .containsExactlyInAnyOrderElementsOf(authors);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.save(author);

        author.setName("Updated Name");
        author.setAge(40);
        underTest.save(author);

        Optional<Author> result = underTest.findById(author.getId());

        Assertions.assertThat(result)
                .isPresent();
        Assertions.assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.save(author);

        underTest.deleteById(author.getId());

        Optional<Author> result = underTest.findById(author.getId());

        Assertions.assertThat(result).isEmpty();
    }
}
