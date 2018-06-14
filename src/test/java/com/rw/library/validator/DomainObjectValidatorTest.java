package com.rw.library.validator;

import com.rw.library.domain.*;
import com.rw.library.domain.definitions.Status;
import com.rw.library.errors.DomainObjectNotFoundException;
import com.rw.library.errors.EntityConstraintViolationException;
import com.rw.library.service.BookServiceImpl;
import com.rw.library.service.BorrowServiceImpl;
import com.rw.library.service.CopyServiceImpl;
import com.rw.library.service.ReaderServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainObjectValidatorTest {
    @InjectMocks
    private DomainObjectValidator validator;

    @Mock
    private ReaderServiceImpl readerService;
    @Mock
    private BookServiceImpl bookService;
    @Mock
    private CopyServiceImpl copyService;
    @Mock
    private BorrowServiceImpl borrowService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testValidateBookIdShouldNotThrowException() {
        //Given
        when(bookService.exists(1L)).thenReturn(true);
        //When & Then
        validator.validateBookId(1L);
    }

    @Test
    public void testValidateBookIdShouldThrowExceptionBookNotFound() {
        //Given
        when(bookService.exists(1L)).thenReturn(false);
        thrown.expect(DomainObjectNotFoundException.class);
        thrown.expectMessage("Could not find " + Book.class.toString() + " id '" + 1 + "'");
        //When & Then
        validator.validateBookId(1L);
    }

    @Test
    public void testValidateCopyIdShouldNotThrowException() {
        //Given
        when(copyService.exists(1L)).thenReturn(true);
        //When & Then
        validator.validateCopyId(1L);
    }

    @Test
    public void testValidateCopyIdShouldThrowExceptionCopyNotFound() {
        //Given
        when(copyService.exists(1L)).thenReturn(false);
        thrown.expect(DomainObjectNotFoundException.class);
        thrown.expectMessage("Could not find " + Copy.class.toString() + " id '" + 1 + "'");
        //When & Then
        validator.validateCopyId(1L);
    }

    @Test
    public void testValidateReaderIdShouldNotThrowException() {
        //Given
        when(readerService.exists(1L)).thenReturn(true);
        //When & Then
        validator.validateReaderId(1L);
    }

    @Test
    public void testValidateReaderIdShouldThrowExceptionReaderNotFound() {
        //Given
        when(readerService.exists(1L)).thenReturn(false);
        thrown.expect(DomainObjectNotFoundException.class);
        thrown.expectMessage("Could not find " + Reader.class.toString() + " id '" + 1 + "'");
        //When & Then
        validator.validateReaderId(1L);
    }

    @Test
    public void testValidateBorrowIdShouldNotThrowException() {
        //Given
        when(borrowService.exists(1L)).thenReturn(true);
        //When & Then
        validator.validateBorrowId(1L);
    }

    @Test
    public void testValidateBorrowIdShouldThrowExceptionBorrowNotFound() {
        //Given
        when(borrowService.exists(1L)).thenReturn(false);
        thrown.expect(DomainObjectNotFoundException.class);
        thrown.expectMessage("Could not find " + Borrow.class.toString() + " id '" + 1 + "'");
        //When & Then
        validator.validateBorrowId(1L);
    }

    @Test
    public void testValidateCopyShouldNotThrowAnyException() {
        //Given
        CopyDto copyDto = new CopyDto();
        copyDto.setBookId(1L);
        copyDto.setStatus(Status.OK);
        when(bookService.exists(1L)).thenReturn(true);
        //When & Then
        validator.validateCopy(copyDto);
    }

    @Test
    public void testValidateCopyShouldThrowExceptionBookIdCanNotBeNull() {
        //Given
        CopyDto copyDto = new CopyDto();
        thrown.expect(EntityConstraintViolationException.class);
        thrown.expectMessage(Copy.class.toString() + ": Book Id can not be null!");
        //When & Then
        validator.validateCopy(copyDto);
    }

    @Test
    public void testValidateCopyShouldThrowExceptionBookNotFound() {
        //Given
        CopyDto copyDto = new CopyDto();
        copyDto.setBookId(1L);
        when(bookService.exists(1L)).thenReturn(false);
        thrown.expect(DomainObjectNotFoundException.class);
        thrown.expectMessage("Could not find " + Book.class.toString() + " id '" + 1 + "'");
        //When & Then
        validator.validateCopy(copyDto);
    }

    @Test
    public void testValidateCopyShouldThrowExceptionWrongCopyStatus() {
        //Given
        CopyDto copyDto = new CopyDto();
        copyDto.setBookId(1L);
        when(bookService.exists(1L)).thenReturn(true);
        thrown.expect(EntityConstraintViolationException.class);
        thrown.expectMessage(Copy.class.toString() + ": wrong name of copy status!");
        //When & Then
        validator.validateCopy(copyDto);
    }

    @Test
    public void testValidateBorrowShouldNotThrowException() {
        //Given
        BorrowDto borrowDto = new BorrowDto();
        borrowDto.setCopyId(1L);
        borrowDto.setReaderId(1L);
        when(copyService.exists(1L)).thenReturn(true);
        when(readerService.exists(1L)).thenReturn(true);
        //When & Then
        validator.validateBorrow(borrowDto);
    }

    @Test
    public void testValidateBorrowShouldThrowExceptionCopyIdCanNotBeNull() {
        //Given
        BorrowDto borrowDto = new BorrowDto();
        thrown.expect(EntityConstraintViolationException.class);
        thrown.expectMessage(Borrow.class.toString() + ": Copy Id can not be null!");
        //When & Then
        validator.validateBorrow(borrowDto);
    }

    @Test
    public void testValidateBorrowShouldThrowExceptionReaderIdCanNotBeNull() {
        //Given
        BorrowDto borrowDto = new BorrowDto();
        borrowDto.setCopyId(1L);
        thrown.expect(EntityConstraintViolationException.class);
        thrown.expectMessage(Borrow.class.toString() + ": Reader Id can not be null!");
        //When & Then
        validator.validateBorrow(borrowDto);
    }

    @Test
    public void testValidateBorrowShouldThrowExceptionCopyNotFound() {
        //Given
        BorrowDto borrowDto = new BorrowDto();
        borrowDto.setCopyId(1L);
        borrowDto.setReaderId(1L);
        when(copyService.exists(1L)).thenReturn(false);
        when(readerService.exists(1L)).thenReturn(false);
        thrown.expect(DomainObjectNotFoundException.class);
        thrown.expectMessage("Could not find " + Copy.class.toString() + " id '" + 1 + "'");
        //When & Then
        validator.validateBorrow(borrowDto);
    }

    @Test
    public void testValidateBorrowShouldThrowExceptionReaderNotFound() {
        //Given
        BorrowDto borrowDto = new BorrowDto();
        borrowDto.setCopyId(1L);
        borrowDto.setReaderId(1L);
        when(copyService.exists(1L)).thenReturn(true);
        when(readerService.exists(1L)).thenReturn(false);
        thrown.expect(DomainObjectNotFoundException.class);
        thrown.expectMessage("Could not find " + Reader.class.toString() + " id '" + 1 + "'");
        //When & Then
        validator.validateBorrow(borrowDto);
    }
}