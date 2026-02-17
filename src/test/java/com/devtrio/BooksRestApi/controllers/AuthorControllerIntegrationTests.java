package com.devtrio.BooksRestApi.controllers;

import com.devtrio.BooksRestApi.TestDataUtil;
import com.devtrio.BooksRestApi.domain.dto.AuthorDto;
import com.devtrio.BooksRestApi.domain.entities.Author;
import com.devtrio.BooksRestApi.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    private AuthorService authorService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTests(
            MockMvc mockMvc, ObjectMapper objectMapper, AuthorService authorService
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.authorService = authorService;
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnHttp201Created() throws Exception {
        Author author = TestDataUtil.createTestAuthor();
        author.setId(null);

        String authorJson = objectMapper.writeValueAsString(author);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnSavedAuthor() throws Exception {
        Author author = TestDataUtil.createTestAuthor();
        author.setId(null);

        String authorJson = objectMapper.writeValueAsString(author);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(25)
        );
    }

    @Test
    public void testThatListAuthorsSuccessfullyReturns200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAuthorsIndexReturnListOfAuthors() throws Exception {
        Author author = TestDataUtil.createTestAuthor();

        authorService.save(author);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(author.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(author.getAge())
        );
    }

    @Test
    public void testThatFindAuthorReturn200OkWhenAuthorExist() throws Exception{
        Author author = TestDataUtil.createTestAuthor();
        authorService.save(author);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFindAuthorReturn404NotFoundWhenAuthorDoseNotExist() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFindAuthorReturnAuthorWhenExist() throws Exception{
        Author author = TestDataUtil.createTestAuthor();
        authorService.save(author);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(author.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(author.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(author.getAge())
        );
    }

    @Test
    public void testThatUpdateAuthorReturnHttpStatus200OkWhenAuthorExists() throws Exception {
        Author author = TestDataUtil.createTestAuthor();
        author = authorService.save(author);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdateAuthorReturnHttpStatus404NotFoundWhenAuthorDoesNotExists() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatUpdateMethodExistsAuthorSuccessfullyUpdated() throws Exception {
        Author author = TestDataUtil.createTestAuthor();
        authorService.save(author);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto.setName("Updated Name");
        authorDto.setAge(37);
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/authors/" + author.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authorDtoJson)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(author.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge()));
    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsHttp200Ok() throws Exception {
        Author author = TestDataUtil.createTestAuthor();
        author = authorService.save(author);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto.setName("Updated Name");
        authorDto.setAge(45);
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateNotExistingAuthorReturnsHttp404NotFound() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto.setName("Updated Name");
        authorDto.setAge(45);
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsUpdatedAuthor() throws Exception {
        Author author = TestDataUtil.createTestAuthor();
        author = authorService.save(author);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto.setName("Updated Name");
        authorDto.setAge(45);
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/authors/" + author.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authorDtoJson)
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").value(author.getId())
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("Updated Name")
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.age").value(45)
                );
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204NoContent() throws Exception {
        Author author = TestDataUtil.createTestAuthor();
        authorService.save(author);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" + author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus404NotFoundIfAuthorDoesNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
}
