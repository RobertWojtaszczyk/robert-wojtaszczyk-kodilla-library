package com.rw.library.service;

import com.rw.library.domain.Book;
import com.rw.library.domain.Copy;
import com.rw.library.domain.definitions.Status;
import com.rw.library.repository.CopyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CopyServiceTest {
    @InjectMocks
    private CopyServiceImpl copyService;

    @Mock
    private CopyRepository copyRepository;

    @Test
    public void shouldFetchOneCopy() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, Status.OK, book);
        when(copyRepository.findOne(1L)).thenReturn(copy);
        //When
        Copy copy1 = copyService.findOne(1L);
        //Then
        assertNotNull(copy1);
        assertEquals(1L, copy1.getId().longValue());
        assertEquals(Status.OK , copy1.getStatus());
        assertEquals(book, copy1.getBook());
    }

    @Test
    public void shouldFetchCopyList() {
        //Given
        List<Copy> copies = new ArrayList<>();
        Book book = new Book(1L, "Title", "Author name");
        copies.add(new Copy(1L, Status.OK, book));
        when(copyRepository.findAll()).thenReturn(copies);
        //When
        List<Copy> copiesList = copyService.findAll();
        //Then
        assertNotNull(copiesList);
        assertEquals(1, copiesList.size());
        assertEquals(1L, copiesList.get(0).getId().longValue());
        assertEquals(Status.OK, copiesList.get(0).getStatus());
        assertEquals(book, copiesList.get(0).getBook());
    }

    @Test
    public void shouldSaveCopy() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, Status.OK, book);
        when(copyRepository.save(copy)).thenReturn(copy);
        //When
        Copy copy1 = copyService.save(copy);
        //Then
        assertNotNull(copy1);
        assertEquals(1L, copy1.getId().longValue());
        assertEquals(Status.OK , copy1.getStatus());
        assertEquals(book, copy1.getBook());
    }

    @Test
    public void shouldCheckIfCopyExist() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, Status.OK, book);
        when(copyRepository.exists(copy.getId())).thenReturn(true);
        //When
        Boolean copyExist = copyService.exists(copy.getId());
        //Then
        assertTrue(copyExist);
    }
}