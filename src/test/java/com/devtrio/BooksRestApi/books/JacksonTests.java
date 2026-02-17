package com.devtrio.BooksRestApi.books;

import com.devtrio.BooksRestApi.domain.entities.Author;
import com.devtrio.BooksRestApi.domain.entities.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

public class JacksonTests {

    private final String json1;
    private final String json2;

    public JacksonTests() {
        json1 = "{\"isbn\":\"987-0-12-47863-5\",\"title\":\"The Enigma of Eternity\",\"author\":{\"id\":null,\"name\":\"Owner name\",\"age\":34},\"year\":\"2005\"}";
        json2 = "{\"foo\":\"bar\",\"isbn\":\"987-0-12-47863-5\",\"title\":\"The Enigma of Eternity\",\"author\":{\"id\":null,\"name\":\"Owner name\",\"age\":34},\"year\":\"2005\"}";
    }

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
                json1
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

        final ObjectMapper objectMapper = new ObjectMapper();

        Book result = objectMapper.readValue(json2, Book.class);

        Assertions.assertThat(result).isEqualTo(book);
    }
}
