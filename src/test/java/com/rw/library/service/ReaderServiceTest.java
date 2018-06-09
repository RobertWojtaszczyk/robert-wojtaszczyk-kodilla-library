package com.rw.library.service;

import com.rw.library.domain.Reader;
import com.rw.library.repository.ReaderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReaderServiceTest {
    @InjectMocks
    private ReaderServiceImpl readerService;

    @Mock
    private ReaderRepository readerRepository;

    @Test
    public void shouldFetchOneReader() {
        //Given
        Reader reader = new Reader(1L, LocalDateTime.now(), "John", "Doe");
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
        readers.add(new Reader(1L, LocalDateTime.now(), "John", "Doe"));
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
    public void shouldSaveReader() {
        //Given
        Reader reader = new Reader(1L, LocalDateTime.now(), "John", "Doe");
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
        Reader reader = new Reader(1L, LocalDateTime.now(), "John", "Doe");
        when(readerRepository.exists(reader.getId())).thenReturn(true);
        //When
        Boolean readerExist = readerService.exists(reader.getId());
        //Then
        assertTrue(readerExist);
    }
}