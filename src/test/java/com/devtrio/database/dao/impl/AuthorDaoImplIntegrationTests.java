package com.devtrio.database.dao.impl;

import com.devtrio.database.TestDataUtil;
import com.devtrio.database.dao.Impl.AuthorDaoImpl;
import com.devtrio.database.domain.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthorDaoImplIntegrationTests {

    private AuthorDaoImpl underTest;

    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void test() {
        Author author = TestDataUtil.createTestAuthor();

        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(author);
    }

}
