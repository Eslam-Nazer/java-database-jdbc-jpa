package com.devtrio.database;

import com.devtrio.database.domain.Author;

public class TestDataUtil {

    public static Author createTestAuthor() {
        return Author.builder().id(1L).name("Abigail Rose").age(25).build();
    }
}
