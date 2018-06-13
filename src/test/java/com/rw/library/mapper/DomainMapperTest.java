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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.rw.library.domain.definitions.Status.OK;
import static org.junit.Assert.*;
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
        Copy copy = new Copy(1L, OK, book);
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
    public void testMapToReadersDtoList() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, OK, book);
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
        Copy copy = new Copy(1L, OK, book);
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
        Copy copy = new Copy(1L, OK, book);
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
        Copy copy = new Copy(1L, OK, book);
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
        Copy copy = new Copy(1L, OK, book);
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

    @Test
    public void testMapToBorrowReturnBookRequest() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, OK, book);
        Reader reader = new Reader(1L, "Adam", "Smith");
        Borrow borrow = new Borrow(1L, LocalDate.now(), reader, copy);
        borrow.setReturnDate(null);
        BorrowDto borrowDto = new BorrowDto();
        borrowDto.setId(1L);
        borrowDto.setReaderId(1L);
        borrowDto.setCopyId(1L);
        borrowDto.setReturnDate(null);
        when(borrowService.findOne(borrowDto.getId())).thenReturn(borrow);
        //When
        Borrow returnedBorrow = domainMapper.mapToBorrowReturnBookRequest(borrowDto.getId());
        //Then
        assertEquals(1L, returnedBorrow.getId().longValue());
        assertEquals(LocalDate.now(), returnedBorrow.getBorrowDate());
        assertEquals(LocalDate.now(), returnedBorrow.getReturnDate());
        assertEquals(reader, returnedBorrow.getReader());
        assertEquals(copy, returnedBorrow.getCopy());
    }

    @Test
    public void testMapToBook() {
        //Given
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Title");
        bookDto.setAuthor("Author");
        //When
        Book book = domainMapper.mapToBook(bookDto);
        //Then
        assertEquals(1L, book.getId().longValue());
        assertEquals("Title", book.getTitle());
        assertEquals("Author", book.getAuthor());
    }

    @Test
    public void testMapToUpdatedBook() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("New Title");
        bookDto.setAuthor("Author");
        when(bookService.findOne(bookDto.getId())).thenReturn(book);
        //When
        Book updatedBook = domainMapper.mapToUpdatedBook(bookDto);
        //Then
        assertEquals(1L, updatedBook.getId().longValue());
        assertEquals("New Title", updatedBook.getTitle());
        assertEquals("Author", updatedBook.getAuthor());
    }

    @Test
    public void testMapToBookDto() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy1 = new Copy(1L, OK, book);
        Copy copy2 = new Copy(2L, OK, book);
        book.getCopies().add(copy1);
        book.getCopies().add(copy2);
        LocalDateTime timestamp = LocalDateTime.now();
        book.setDateCreated(timestamp);
        book.setLastUpdated(timestamp);
        List<Copy> availableCopies = new ArrayList<>();
        availableCopies.add(copy1);
        when(copyService.getAvailableCopies(book.getId())).thenReturn(availableCopies);
        //When
        BookDto bookDto = domainMapper.mapToBookDto(book);
        //Then
        assertEquals(1L, bookDto.getId().longValue());
        assertEquals(timestamp.toString(), bookDto.getDateCreated());
        assertEquals(timestamp.toString(), bookDto.getLastUpdated());
        assertEquals("Title", bookDto.getTitle());
        assertEquals("Author name", bookDto.getAuthor());
        assertEquals(2, bookDto.getTotalCopiesInLibrary());
        assertEquals(1, bookDto.getCopiesAvailable());
    }

    @Test
    public void testMapToBookDtoList() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy1 = new Copy(1L, OK, book);
        Copy copy2 = new Copy(2L, OK, book);
        book.getCopies().add(copy1);
        book.getCopies().add(copy2);
        LocalDateTime timestamp = LocalDateTime.now();
        book.setDateCreated(timestamp);
        book.setLastUpdated(timestamp);
        List<Copy> availableCopies = new ArrayList<>();
        availableCopies.add(copy1);
        List<Book> books = new ArrayList<>();
        books.add(book);
        when(copyService.getAvailableCopies(book.getId())).thenReturn(availableCopies);
        //When
        List<BookDto> bookDtos = domainMapper.mapToBookDtoList(books);
        //Then
        assertEquals(1L, bookDtos.get(0).getId().longValue());
        assertEquals(timestamp.toString(), bookDtos.get(0).getDateCreated());
        assertEquals(timestamp.toString(), bookDtos.get(0).getLastUpdated());
        assertEquals("Title", bookDtos.get(0).getTitle());
        assertEquals("Author name", bookDtos.get(0).getAuthor());
        assertEquals(2, bookDtos.get(0).getTotalCopiesInLibrary());
        assertEquals(1, bookDtos.get(0).getCopiesAvailable());
    }

    @Test
    public void testMapToBookDtoPage() {
        //Given
        List<Book> books = new ArrayList<>();
        Book book = new Book(1L, "Title", "Author name");
        Copy copy1 = new Copy(1L, OK, book);
        Copy copy2 = new Copy(2L, OK, book);
        book.getCopies().add(copy1);
        book.getCopies().add(copy2);
        LocalDateTime timestamp = LocalDateTime.now();
        book.setDateCreated(timestamp);
        book.setLastUpdated(timestamp);
        List<Copy> availableCopies = new ArrayList<>();
        availableCopies.add(copy1);
        when(copyService.getAvailableCopies(book.getId())).thenReturn(availableCopies);
        books.add(book);
        Page<Book> bookPage = new PageImpl(books);
        //When
        Page<BookDto> bookDtoPage = domainMapper.mapToBookDtoPage(bookPage);
        //Then
        assertNotNull(bookDtoPage);
        assertEquals(1, bookDtoPage.getTotalElements());
        assertEquals(1, bookDtoPage.getTotalPages());
        assertEquals(1, bookDtoPage.getContent().size());
        assertEquals(1L, bookDtoPage.getContent().get(0).getId().longValue());
        assertEquals(timestamp.toString(), bookDtoPage.getContent().get(0).getDateCreated());
        assertEquals(timestamp.toString(), bookDtoPage.getContent().get(0).getLastUpdated());
        assertEquals("Title", bookDtoPage.getContent().get(0).getTitle());
        assertEquals("Author name", bookDtoPage.getContent().get(0).getAuthor());
        assertEquals(2, bookDtoPage.getContent().get(0).getTotalCopiesInLibrary());
        assertEquals(1, bookDtoPage.getContent().get(0).getCopiesAvailable());
    }

    @Test
    public void testMapToCopy() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        CopyDto copyDto = new CopyDto();
        copyDto.setId(1L);
        copyDto.setStatus(OK);
        copyDto.setBookId(1L);
        when(bookService.findOne(copyDto.getBookId())).thenReturn(book);
        //When
        Copy copy = domainMapper.mapToCopy(copyDto);
        //Then
        assertEquals(1L, copy.getId().longValue());
        assertEquals(OK, copy.getStatus());
        assertEquals(book, copy.getBook());
    }

    @Test
    public void mapToUpdatedCopy() {
        Book book1 = new Book(1L, "Title", "Author name");
        Book book2 = new Book(2L, "New Title", "New author name");
        Copy copy = new Copy(1L, OK, book1);

        CopyDto copyDto = new CopyDto();
        copyDto.setId(1L);
        copyDto.setStatus(Status.DAMAGED);
        copyDto.setBookId(2L);
        when(copyService.findOne(copyDto.getId())).thenReturn(copy);
        when(bookService.findOne(copyDto.getBookId())).thenReturn(book2);
        //When
        Copy updatedCopy = domainMapper.mapToUpdatedCopy(copyDto);
        //Then
        assertEquals(1L, updatedCopy.getId().longValue());
        assertEquals(Status.DAMAGED, updatedCopy.getStatus());
        assertEquals(book2, updatedCopy.getBook());
    }

    @Test
    public void testMapToCopyDto() {
            //Given
            Book book = new Book(1L, "Title", "Author name");
            Copy copy = new Copy(1L, OK, book);
            LocalDateTime timestamp = LocalDateTime.now();
            copy.setDateCreated(timestamp);
            copy.setLastUpdated(timestamp);
            //When
            CopyDto copyDto = domainMapper.mapToCopyDto(copy);
            //Then
            assertEquals(1L, copyDto.getId().longValue());
            assertEquals(timestamp.toLocalDate().toString(), copyDto.getDateCreated());
            assertEquals(timestamp.toLocalDate().toString(), copyDto.getLastUpdated());
            assertEquals(OK, copyDto.getStatus());
            assertEquals(book.getId(), copyDto.getBookId());
    }

    @Test
    public void testMapToCopyDtoList() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, OK, book);
        LocalDateTime timestamp = LocalDateTime.now();
        copy.setDateCreated(timestamp);
        copy.setLastUpdated(timestamp);
        List<Copy> copies = new ArrayList<>();
        copies.add(copy);
        //When
        List<CopyDto> copyDtos = domainMapper.mapToCopyDtoList(copies);
        //Then
        assertEquals(1L, copyDtos.get(0).getId().longValue());
        assertEquals(timestamp.toLocalDate().toString(), copyDtos.get(0).getDateCreated());
        assertEquals(timestamp.toLocalDate().toString(), copyDtos.get(0).getLastUpdated());
        assertEquals(OK, copyDtos.get(0).getStatus());
        assertEquals(book.getId(), copyDtos.get(0).getBookId());
    }

    @Test
    public void testMapToCopyDtoPage() {
        //Given
        List<Copy> copies = new ArrayList<>();
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, OK, book);
        LocalDateTime timestamp = LocalDateTime.now();
        copy.setDateCreated(timestamp);
        copy.setLastUpdated(timestamp);
        copies.add(copy);
        Page<Copy> copyPage = new PageImpl(copies);
        //When
        Page<CopyDto> copyDtoPage = domainMapper.mapToCopyDtoPage(copyPage);
        //Then
        assertNotNull(copyDtoPage);
        assertEquals(1, copyDtoPage.getTotalElements());
        assertEquals(1, copyDtoPage.getTotalPages());
        assertEquals(1, copyDtoPage.getContent().size());
        assertEquals(1L, copyDtoPage.getContent().get(0).getId().longValue());
        assertEquals(timestamp.toLocalDate().toString(), copyDtoPage.getContent().get(0).getDateCreated());
        assertEquals(timestamp.toLocalDate().toString(), copyDtoPage.getContent().get(0).getLastUpdated());
        assertEquals(OK, copyDtoPage.getContent().get(0).getStatus());
        assertEquals(book.getId(), copyDtoPage.getContent().get(0).getBookId());
    }

    @Test
    public void testMapToBorrowedDtoOverdueIsFalse() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, OK, book);
        Reader reader = new Reader(1L, "Adam", "Smith");
        Borrow borrow = new Borrow(1L, LocalDate.now(), reader, copy);
        borrow.setBorrowDate(LocalDate.now().minusDays(5));
        //When
        BorrowedDto borrowedDto = domainMapper.mapToBorrowedDto(borrow);
        //Then
        assertEquals(1L, borrowedDto.getId().longValue());
        assertEquals("Title", borrowedDto.getTitle());
        assertEquals("Author name", borrowedDto.getAuthor());
        assertEquals(LocalDate.now().minusDays(5).toString(), borrowedDto.getBorrowDate());
        assertEquals(LocalDate.now().plusDays(25).toString(), borrowedDto.getReturnDate());
        assertFalse(borrowedDto.isOverdue());
        assertEquals(reader.getId(), borrowedDto.getReaderId());
    }

    @Test
    public void testMapToBorrowedDtoOverdueIsTrue() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, OK, book);
        Reader reader = new Reader(1L, "Adam", "Smith");
        Borrow borrow = new Borrow(1L, LocalDate.now(), reader, copy);
        borrow.setBorrowDate(LocalDate.now().minusDays(35));
        //When
        BorrowedDto borrowedDto = domainMapper.mapToBorrowedDto(borrow);
        //Then
        assertEquals(1L, borrowedDto.getId().longValue());
        assertEquals("Title", borrowedDto.getTitle());
        assertEquals("Author name", borrowedDto.getAuthor());
        assertEquals(LocalDate.now().minusDays(35).toString(), borrowedDto.getBorrowDate());
        assertEquals(LocalDate.now().minusDays(5).toString(), borrowedDto.getReturnDate());
        assertTrue(borrowedDto.isOverdue());
        assertEquals(reader.getId(), borrowedDto.getReaderId());
    }

    @Test
    public void testMapToBorrowedDtoList() {
        //Given
        Book book = new Book(1L, "Title", "Author name");
        Copy copy = new Copy(1L, OK, book);
        Reader reader = new Reader(1L, "Adam", "Smith");
        Borrow borrow = new Borrow(1L, LocalDate.now(), reader, copy);
        borrow.setBorrowDate(LocalDate.now().minusDays(35));
        List<Borrow> borrowList = new ArrayList<>();
        borrowList.add(borrow);
        //When
        List<BorrowedDto> borrowedDtos = domainMapper.mapToBorrowedList(borrowList);
        //Then
        assertEquals(1, borrowedDtos.size());
        assertEquals(1L, borrowedDtos.get(0).getId().longValue());
        assertEquals("Title", borrowedDtos.get(0).getTitle());
        assertEquals("Author name", borrowedDtos.get(0).getAuthor());
        assertEquals(LocalDate.now().minusDays(35).toString(), borrowedDtos.get(0).getBorrowDate());
        assertEquals(LocalDate.now().minusDays(5).toString(), borrowedDtos.get(0).getReturnDate());
        assertTrue(borrowedDtos.get(0).isOverdue());
        assertEquals(reader.getId(), borrowedDtos.get(0).getReaderId());
    }
}