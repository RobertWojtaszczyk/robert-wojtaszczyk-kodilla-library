package com.rw.library;

import com.rw.library.domain.Book;
import com.rw.library.errors.RestResponseEntityExceptionHandler;
import com.rw.library.repository.BookRepository;
import com.rw.library.repository.CopyRepository;
import com.rw.library.service.BookServiceImpl;
import com.rw.library.service.BorrowServiceImpl;
import com.rw.library.service.CopyServiceImpl;
import com.rw.library.service.ReaderServiceImpl;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolationException;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestResponseEntityExceptionHandlerTest {
    @InjectMocks
    private RestResponseEntityExceptionHandler exceptionHandler;

    @Mock
    private ReaderServiceImpl readerService;
    @Mock
    private BookServiceImpl bookService;
    @Mock
    private CopyServiceImpl copyService;
    @Mock
    private BorrowServiceImpl borrowService;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CopyRepository copyRepository;

    @After
    public void cleanUp() {
        bookRepository.deleteAll();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void  shouldThrowConstraintViolationException() {
        //Given
        Book book = new Book(1L, "T", "Author");
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Please enter book title");
        //When & Then
        bookRepository.save(book);
    }

    @Test
    public void  shouldThrowTransactionSystemException() {
        //Given
        Book book = new Book();
        book.setTitle("Title");
        book.setAuthor("Author");
        bookRepository.save(book);
        book.setTitle("T");
        thrown.expect(TransactionSystemException.class);
        thrown.expectMessage("Could not commit JPA transaction");
        //When & Then
        bookRepository.save(book);
    }

    @Test
    public void  shouldThrowEmptyResultDataAccessException() {
        //Given
        thrown.expect(EmptyResultDataAccessException.class);
        thrown.expectMessage("No class");
        //When & Then
        bookRepository.delete(1L);
    }
}
