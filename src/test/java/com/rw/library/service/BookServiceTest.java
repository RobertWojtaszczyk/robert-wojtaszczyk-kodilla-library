package com.rw.library.service;

import com.rw.library.domain.Book;
import com.rw.library.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        Book book = new Book(1L, "Title", "Author name");
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
        books.add(new Book(1L, "Title", "Author name"));
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
    public void shouldFetchPageOfBookList() {
        //Given
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Title", "Author name"));
        Pageable pageable = new PageRequest(0,5);
        Page<Book> expectedPage = new PageImpl(books);
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);
        //When
        Page<Book> returnedBooksPage = bookService.findAll(pageable);
        //Then
        assertNotNull(returnedBooksPage);
        assertEquals(1, returnedBooksPage.getTotalElements());
        assertEquals(1, returnedBooksPage.getTotalPages());
        assertEquals(1, returnedBooksPage.getContent().size());
        assertEquals(1L, returnedBooksPage.getContent().get(0).getId().longValue());
        assertEquals("Title", returnedBooksPage.getContent().get(0).getTitle());
        assertEquals("Author name", returnedBooksPage.getContent().get(0).getAuthor());
        assertEquals(expectedPage, returnedBooksPage);
    }

    @Test
    public void shouldSaveBook() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
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
        Book book = new Book(1L, "Title", "Author name");
        when(bookRepository.exists(book.getId())).thenReturn(true);
        //When
        Boolean bookExist = bookService.exists(book.getId());
        //Then
        assertTrue(bookExist);
    }

    @Test
    public void shouldCallDeleteMethod() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        //When
        bookService.delete(book.getId());
        //Then
        verify(bookRepository, times(1)).delete(book.getId());
    }
}