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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

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
    public void shouldFetchPageOfCopyList() {
        //Given
        List<Copy> copies = new ArrayList<>();
        Book book = new Book(1L, "Title", "Author name");
        copies.add(new Copy(1L, Status.OK, book));
        Pageable pageable = new PageRequest(0,5);
        Page<Copy> expectedPage = new PageImpl(copies);
        when(copyRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);
        //When
        Page<Copy> returnedCopiesPage = copyService.findAll(pageable);
        //Then
        assertNotNull(returnedCopiesPage);
        assertEquals(1, returnedCopiesPage.getTotalElements());
        assertEquals(1, returnedCopiesPage.getTotalPages());
        assertEquals(1, returnedCopiesPage.getContent().size());
        assertEquals(1L, returnedCopiesPage.getContent().get(0).getId().longValue());
        assertEquals(Status.OK, returnedCopiesPage.getContent().get(0).getStatus());
        assertEquals(book, returnedCopiesPage.getContent().get(0).getBook());
        assertEquals(expectedPage, returnedCopiesPage);
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

    @Test
    public void shouldCallDeleteMethod() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, Status.OK, book);
        //When
        copyService.delete(copy.getId());
        //Then
        verify(copyRepository, times(1)).delete(copy.getId());
    }

    @Test
    public void shouldFetchPageOfCopiesForGivenBookId() {
        //Given
        List<Copy> copies = new ArrayList<>();
        Book book = new Book(1L, "Title", "Author name");
        copies.add(new Copy(1L, Status.OK, book));
        Pageable pageable = new PageRequest(0,5);
        Page<Copy> expectedPage = new PageImpl(copies);
        when(copyRepository.findAllByBookId(pageable, book.getId())).thenReturn(expectedPage);
        //When
        Page<Copy> returnedCopiesPage = copyService.findAllByBookId(pageable, 1L);
        //Then
        assertNotNull(returnedCopiesPage);
        assertEquals(1, returnedCopiesPage.getTotalElements());
        assertEquals(1, returnedCopiesPage.getTotalPages());
        assertEquals(1, returnedCopiesPage.getContent().size());
        assertEquals(1L, returnedCopiesPage.getContent().get(0).getId().longValue());
        assertEquals(Status.OK, returnedCopiesPage.getContent().get(0).getStatus());
        assertEquals(book, returnedCopiesPage.getContent().get(0).getBook());
        assertEquals(expectedPage, returnedCopiesPage);
    }

    @Test
    public void souldFetchAvailableCopies() {
        //Given
        List<Copy> copies = new ArrayList<>();
        Book book = new Book(1L, "Title", "Author name");
        copies.add(new Copy(1L, Status.OK, book));
        copies.add(new Copy(2L, Status.OK, book));
        when(copyRepository.getAvailableCopies(book.getId())).thenReturn(copies);
        //When
        List<Copy> copiesList = copyService.getAvailableCopies(1L);
        //Then
        assertNotNull(copiesList);
        assertEquals(2, copiesList.size());
        assertEquals(1L, copiesList.get(0).getId().longValue());
        assertEquals(Status.OK, copiesList.get(0).getStatus());
        assertEquals(book, copiesList.get(0).getBook());
    }
}