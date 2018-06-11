package com.rw.library.service;

import com.rw.library.domain.Book;
import com.rw.library.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void shouldFetchOneBook() {
        //Given
        Book book = new Book(1L, LocalDateTime.now(), "Title", "Author name");
        when(bookRepository.findOne(1L)).thenReturn(book);
        //When
        Book book1 = bookService.findOne(1L);
        //Then
        assertNotNull(book1);
        assertEquals(1L, book1.getId().longValue());
        assertEquals("Title", book1.getTitle());
        assertEquals("Author name", book1.getAuthor());
    }

    @Test
    public void shouldFetchBookList() {
        //Given
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, LocalDateTime.now(), "Title", "Author name"));
        when(bookRepository.findAll()).thenReturn(books);
        //When
        List<Book> booksList = bookService.findAll();
        //Then
        assertNotNull(booksList);
        assertEquals(1, booksList.size());
        assertEquals(1L, booksList.get(0).getId().longValue());
        assertEquals("Title", booksList.get(0).getTitle());
        assertEquals("Author name", booksList.get(0).getAuthor());
    }

    @Test
    public void shouldSaveBook() {
        //Given
        Book book = new Book(1L, LocalDateTime.now(), "Title", "Author name");
        when(bookRepository.save(book)).thenReturn(book);
        //When
        Book book1 = bookService.save(book);
        //Then
        assertNotNull(book1);
        assertEquals(1L, book1.getId().longValue());
        assertEquals("Title", book1.getTitle());
        assertEquals("Author name", book1.getAuthor());
    }

    @Test
    public void shouldCheckIfBookExist() {
        //Given
        Book book = new Book(1L, LocalDateTime.now(), "Title", "Author name");
        when(bookRepository.exists(book.getId())).thenReturn(true);
        //When
        Boolean bookExist = bookService.exists(book.getId());
        //Then
        assertTrue(bookExist);
    }
}