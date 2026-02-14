package com.devtrio.BooksRestApi.services.implemention;

import com.devtrio.BooksRestApi.domain.entities.Book;
import com.devtrio.BooksRestApi.repositories.BookRepository;
import com.devtrio.BooksRestApi.services.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book create(String isbn, Book book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }
}
