package com.devtrio.BooksRestApi.services.implemention;

import com.devtrio.BooksRestApi.domain.entities.Book;
import com.devtrio.BooksRestApi.repositories.BookRepository;
import com.devtrio.BooksRestApi.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<Book> findAll() {
        Spliterator<Book> spliterator = bookRepository.findAll().spliterator();
        return  StreamSupport.stream(spliterator, false).collect(Collectors.toList());
    }
}
