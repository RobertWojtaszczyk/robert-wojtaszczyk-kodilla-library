package com.rw.library.mapper;

import com.rw.library.domain.*;
import com.rw.library.domain.definitions.Status;
import com.rw.library.service.BookServiceImpl;
import com.rw.library.service.BorrowServiceImpl;
import com.rw.library.service.CopyServiceImpl;
import com.rw.library.service.ReaderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainMapperTest {
    @InjectMocks
    private DomainMapper domainMapper;

    @Mock
    private ReaderServiceImpl readerService;
    @Mock
    private BookServiceImpl bookService;
    @Mock
    private CopyServiceImpl copyService;
    @Mock
    private BorrowServiceImpl borrowService;

    @Test
    public void testMapToReader() {
        //Given
        ReaderDto readerDto = new ReaderDto();
        readerDto.setId(1L);
        readerDto.setFirstname("John");
        readerDto.setLastname("Doe");
        //When
        Reader reader = domainMapper.mapToReader(readerDto);
        //Then
        assertEquals(1L, reader.getId().longValue());
        assertEquals("John", reader.getFirstname());
        assertEquals("Doe", reader.getLastname());
    }

    @Test
    public void testMapToUpdatedReader() {
        //Given
        Reader reader = new Reader(1L, "Adam", "Smith");
        ReaderDto readerDto = new ReaderDto();
        readerDto.setId(1L);
        readerDto.setFirstname("John");
        readerDto.setLastname("Doe");
        when(readerService.findOne(readerDto.getId())).thenReturn(reader);
        //When
        Reader updatedReader = domainMapper.mapToUpdatedReader(readerDto);
        //Then
        assertEquals(1L, updatedReader.getId().longValue());
        assertEquals("John", updatedReader.getFirstname());
        assertEquals("Doe", updatedReader.getLastname());
    }

    @Test
    public void testMapToReaderDto() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, Status.OK, book);
        Reader reader = new Reader(1L, "Adam", "Smith");
        Borrow borrow = new Borrow(1L, LocalDate.now(), reader, copy);
        List<Borrow> borrows = new ArrayList<>();
        borrows.add(borrow);
        LocalDateTime dateTime = LocalDateTime.now();
        reader.setDateCreated(dateTime);
        reader.setLastUpdated(dateTime);
        reader.getBorrows().add(borrow);
        when(borrowService.getBorrowsForReaderId(reader.getId())).thenReturn(borrows);
        //When
        ReaderDto readerDto = domainMapper.mapToReaderDto(reader);
        //Then
        assertEquals(1L, readerDto.getId().longValue());
        assertEquals("Adam", readerDto.getFirstname());
        assertEquals("Smith", readerDto.getLastname());
        assertEquals(dateTime.toString(), readerDto.getDateCreated());
        assertEquals(dateTime.toString(), readerDto.getLastUpdated());
        assertEquals(1, readerDto.getTotalBorrowedBooks());
        assertEquals(1, readerDto.getCurrentlyBorrowedBooks());
    }

    @Test
    public void testMapToReaderSDtoList() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, Status.OK, book);
        Reader reader = new Reader(1L, "Adam", "Smith");
        Borrow borrow = new Borrow(1L, LocalDate.now(), reader, copy);
        List<Borrow> borrows = new ArrayList<>();
        borrows.add(borrow);
        LocalDateTime dateTime = LocalDateTime.now();
        reader.setDateCreated(dateTime);
        reader.setLastUpdated(dateTime);
        reader.getBorrows().add(borrow);
        List<Reader> readers = new ArrayList<>();
        readers.add(reader);
        when(borrowService.getBorrowsForReaderId(reader.getId())).thenReturn(borrows);
        //When
        List<ReaderDto> readerDtoList = domainMapper.mapToReadersDtoList(readers);
        //Then
        assertEquals(1, readerDtoList.size());
        assertEquals(1L, readerDtoList.get(0).getId().longValue());
        assertEquals("Adam", readerDtoList.get(0).getFirstname());
        assertEquals("Smith", readerDtoList.get(0).getLastname());
        assertEquals(dateTime.toString(), readerDtoList.get(0).getDateCreated());
        assertEquals(dateTime.toString(), readerDtoList.get(0).getLastUpdated());
        assertEquals(1, readerDtoList.get(0).getTotalBorrowedBooks());
        assertEquals(1, readerDtoList.get(0).getCurrentlyBorrowedBooks());
    }

    @Test
    public void testMapToBorrow() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, Status.OK, book);
        Reader reader = new Reader(1L, "Adam", "Smith");
        BorrowDto borrowDto = new BorrowDto();
        borrowDto.setId(1L);
        borrowDto.setReaderId(1L);
        borrowDto.setCopyId(1L);
        when(readerService.findOne(borrowDto.getReaderId())).thenReturn(reader);
        when(copyService.findOne(borrowDto.getCopyId())).thenReturn(copy);
        //When
        Borrow borrow = domainMapper.mapToBorrow(borrowDto);
        //Then
        assertEquals(1L, borrow.getId().longValue());
        assertEquals(LocalDate.now(), borrow.getBorrowDate());
        assertEquals(reader, borrow.getReader());
        assertEquals(copy, borrow.getCopy());
    }

    @Test
    public void testMapToUpdatedBorrow() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, Status.OK, book);
        Reader reader = new Reader(1L, "Adam", "Smith");
        Borrow borrow = new Borrow(1L, LocalDate.now(), null, null);
        BorrowDto borrowDto = new BorrowDto();
        borrowDto.setId(1L);
        borrowDto.setReaderId(1L);
        borrowDto.setCopyId(1L);
        when(borrowService.findOne(borrowDto.getId())).thenReturn(borrow);
        when(readerService.findOne(borrowDto.getReaderId())).thenReturn(reader);
        when(copyService.findOne(borrowDto.getCopyId())).thenReturn(copy);
        //When
        Borrow updatedBorrow = domainMapper.mapToUpdatedBorrow(borrowDto);
        //Then
        assertEquals(1L, updatedBorrow.getId().longValue());
        assertEquals(LocalDate.now(), updatedBorrow.getBorrowDate());
        assertEquals(reader, updatedBorrow.getReader());
        assertEquals(copy, updatedBorrow.getCopy());
    }

    @Test
    public void testMapToBorrowDto() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, Status.OK, book);
        Reader reader = new Reader(1L, "Adam", "Smith");
        Borrow borrow = new Borrow(1L, LocalDate.now(), reader, copy);
        LocalDateTime timestamp = LocalDateTime.now();
        borrow.setDateCreated(timestamp);
        borrow.setLastUpdated(timestamp);
        //When
        BorrowDto borrowDto = domainMapper.mapToBorrowDto(borrow);
        //Then
        assertEquals(1L, borrowDto.getId().longValue());
        assertEquals(timestamp.toString(), borrowDto.getDateCreated());
        assertEquals(timestamp.toString(), borrowDto.getLastUpdated());
        assertEquals(LocalDate.now().toString(), borrowDto.getBorrowDate());
        assertEquals(1L, borrowDto.getReaderId().longValue());
        assertEquals(1L, borrowDto.getCopyId().longValue());
    }

    @Test
    public void testMapToBorrowsDtoList() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, Status.OK, book);
        Reader reader = new Reader(1L, "Adam", "Smith");
        Borrow borrow = new Borrow(1L, LocalDate.now(), reader, copy);
        LocalDateTime timestamp = LocalDateTime.now();
        borrow.setDateCreated(timestamp);
        borrow.setLastUpdated(timestamp);
        List<Borrow> borrowList = new ArrayList<>();
        borrowList.add(borrow);
        //When
        List<BorrowDto> borrowDtoList = domainMapper.mapToBorrowsDtoList(borrowList);
        //Then
        assertEquals(1, borrowDtoList.size());
        assertEquals(1L, borrowDtoList.get(0).getId().longValue());
        assertEquals(timestamp.toString(), borrowDtoList.get(0).getDateCreated());
        assertEquals(timestamp.toString(), borrowDtoList.get(0).getLastUpdated());
        assertEquals(LocalDate.now().toString(), borrowDtoList.get(0).getBorrowDate());
        assertEquals(1L, borrowDtoList.get(0).getReaderId().longValue());
        assertEquals(1L, borrowDtoList.get(0).getCopyId().longValue());
    }
}


        //Given

        //When

        //Then

/*      */