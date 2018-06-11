package com.rw.library.service;

import com.rw.library.domain.Book;
import com.rw.library.domain.Borrow;
import com.rw.library.domain.Copy;
import com.rw.library.domain.Reader;
import com.rw.library.domain.definitions.Status;
import com.rw.library.repository.BorrowRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BorrowServiceTest {
    @InjectMocks
    private BorrowServiceImpl borrowService;

    @Mock
    private BorrowRepository borrowRepository;

    @Test
    public void shouldFetchOneBorrow() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, Status.OK, book);
        Reader reader = new Reader(1L, "John", "Doe");
        Borrow borrow = new Borrow(1L, LocalDate.now(), reader, copy);
        when(borrowRepository.findOne(1L)).thenReturn(borrow);
        //When
        Borrow borrow1 = borrowService.findOne(1L);
        //Then
        assertNotNull(borrow1);
        assertEquals(1L, borrow1.getId().longValue());
        assertEquals(LocalDate.now(), borrow1.getBorrowDate());
        assertEquals(copy, borrow1.getCopy());
        assertEquals(reader, borrow1.getReader());
    }

    @Test
    public void shouldFetchBorrowList() {
        //Given
        List<Borrow> borrows = new ArrayList<>();
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, Status.OK, book);
        Reader reader = new Reader(1L, "John", "Doe");
        borrows.add(new Borrow(1L, LocalDate.now(), reader, copy));
        when(borrowRepository.findAll()).thenReturn(borrows);
        //When
        List<Borrow> borrowsList = borrowService.findAll();
        //Then
        assertNotNull(borrowsList);
        assertEquals(1, borrowsList.size());
        assertEquals(1L, borrowsList.get(0).getId().longValue());
        assertEquals(LocalDate.now(), borrowsList.get(0).getBorrowDate());
        assertEquals(copy, borrowsList.get(0).getCopy());
        assertEquals(reader, borrowsList.get(0).getReader());
    }

    @Test
    public void shouldSaveBorrow() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, Status.OK, book);
        Reader reader = new Reader(1L, "John", "Doe");
        Borrow borrow = new Borrow(1L, LocalDate.now(), reader, copy);
        when(borrowRepository.save(borrow)).thenReturn(borrow);
        //When
        Borrow borrow1 = borrowService.save(borrow);
        //Then
        assertNotNull(borrow1);
        assertEquals(1L, borrow1.getId().longValue());
        assertEquals(LocalDate.now(), borrow1.getBorrowDate());
        assertEquals(copy, borrow1.getCopy());
        assertEquals(reader, borrow1.getReader());
    }

    @Test
    public void shouldCheckIfBorrowExist() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, Status.OK, book);
        Reader reader = new Reader(1L, "John", "Doe");
        Borrow borrow = new Borrow(1L, LocalDate.now(), reader, copy);
        when(borrowRepository.exists(borrow.getId())).thenReturn(true);
        //When
        Boolean borrowExist = borrowService.exists(borrow.getId());
        //Then
        assertTrue(borrowExist);
    }
}