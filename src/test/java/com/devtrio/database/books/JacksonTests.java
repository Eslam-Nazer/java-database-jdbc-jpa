package com.devtrio.database.books;

import com.devtrio.database.domain.Author;
import com.devtrio.database.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

public class JacksonTests {

    @Test
    public void testThatObjectMapperCanCreateJsonFromJavaObject() {
        ObjectMapper objectMapper = new ObjectMapper();

        Book book = Book.builder()
                .isbn("987-0-12-47863-5")
                .title("The Enigma of Eternity")
                .author(new Author(null, "Owner name", 34))
                .yearPublished("2005")
                .build();

        String result = objectMapper.writeValueAsString(book);

        Assertions.assertThat(result).isEqualTo(
                "{\"isbn\":\"987-0-12-47863-5\",\"title\":\"The Enigma of Eternity\",\"author\":{\"id\":null,\"name\":\"Owner name\",\"age\":34},\"yearPublished\":\"2005\"}"
        );
    }

    @Test
    public void testThatObjectMapperCanCreateJavaObjectFromJsonObject() {
        Book book = Book.builder()
                .isbn("987-0-12-47863-5")
                .title("The Enigma of Eternity")
                .author(new Author(null, "Owner name", 34))
                .yearPublished("2005")
                .build();

        String json = "{\"isbn\":\"987-0-12-47863-5\",\"title\":\"The Enigma of Eternity\",\"author\":{\"id\":null,\"name\":\"Owner name\",\"age\":34},\"yearPublished\":\"2005\"}";

        final ObjectMapper objectMapper = new ObjectMapper();

        Book result = objectMapper.readValue(json, Book.class);

        Assertions.assertThat(result).isEqualTo(book);
    }
}
