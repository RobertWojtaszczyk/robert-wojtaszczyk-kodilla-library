package com.rw.library.service;

import com.rw.library.domain.Reader;
import com.rw.library.repository.ReaderRepository;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@RunWith(MockitoJUnitRunner.class)
public class ReaderServiceTest {
    @InjectMocks
    private ReaderServiceImpl readerService;

    @Mock
    private ReaderRepository readerRepository;

    @Test
    public void shouldFetchOneReader() {
        //Given
        Reader reader = new Reader(1L, "John", "Doe");
        when(readerRepository.findOne(1L)).thenReturn(reader);
        //When
        Reader reader1 = readerService.findOne(1L);
        //Then
        assertNotNull(reader1);
        assertEquals(1L, reader1.getId().longValue());
        assertEquals("John", reader1.getFirstname());
        assertEquals("Doe", reader1.getLastname());
    }

    @Test
    public void shouldFetchReaderList() {
        //Given
        List<Reader> readers = new ArrayList<>();
        readers.add(new Reader(1L, "John", "Doe"));
        when(readerRepository.findAll()).thenReturn(readers);
        //When
        List<Reader> readersList = readerService.findAll();
        //Then
        assertNotNull(readersList);
        assertEquals(1, readersList.size());
        assertEquals(1L, readersList.get(0).getId().longValue());
        assertEquals("John", readersList.get(0).getFirstname());
        assertEquals("Doe", readersList.get(0).getLastname());
    }

    @Test
    public void shouldFetchPageOfReaderList() {
        //Given
        List<Reader> readers = new ArrayList<>();
        readers.add(new Reader(1L, "John", "Doe"));
        Pageable pageable = new PageRequest(0,5);
        Page<Reader> expectedPage = new PageImpl(readers);
        when(readerRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);
        //When
        Page<Reader> returnedReadersPage = readerService.findAll(pageable);
        //Then
        assertNotNull(returnedReadersPage);
        assertEquals(1, returnedReadersPage.getTotalElements());
        assertEquals(1, returnedReadersPage.getTotalPages());
        assertEquals(1, returnedReadersPage.getContent().size());
        assertEquals(1L, returnedReadersPage.getContent().get(0).getId().longValue());
        assertEquals("John", returnedReadersPage.getContent().get(0).getFirstname());
        assertEquals("Doe", returnedReadersPage.getContent().get(0).getLastname());
        assertEquals(expectedPage, returnedReadersPage);
    }

    @Test
    public void shouldSaveReader() {
        //Given
        Reader reader = new Reader(1L, "John", "Doe");
        when(readerRepository.save(reader)).thenReturn(reader);
        //When
        Reader reader1 = readerService.save(reader);
        //Then
        assertNotNull(reader1);
        assertEquals(1L, reader1.getId().longValue());
        assertEquals("John", reader1.getFirstname());
        assertEquals("Doe", reader1.getLastname());
    }

    @Test
    public void shouldCheckIfReaderExist() {
        //Given
        Reader reader = new Reader(1L, "John", "Doe");
        when(readerRepository.exists(reader.getId())).thenReturn(true);
        //When
        Boolean readerExist = readerService.exists(reader.getId());
        //Then
        assertTrue(readerExist);
    }

    @Test
    public void shouldCallDeleteMethod() {
        //Given
        Reader reader = new Reader(1L, "John", "Doe");
        //When
        readerService.delete(reader.getId());
        //Then
        verify(readerRepository, times(1)).delete(reader.getId());
    }
}