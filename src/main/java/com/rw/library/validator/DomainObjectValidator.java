package com.rw.library.validator;

import com.rw.library.domain.BookDto;
import com.rw.library.domain.BorrowDto;
import com.rw.library.domain.CopyDto;
import com.rw.library.domain.ReaderDto;
import com.rw.library.domain.definitions.Status;
import com.rw.library.errors.DomainObjectNotFoundException;
import com.rw.library.errors.EntityConstraintViolationException;
import com.rw.library.service.BookService;
import com.rw.library.service.BorrowService;
import com.rw.library.service.CopyService;
import com.rw.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class DomainObjectValidator {
    private final BookService bookService;
    private final CopyService copyService;
    private final ReaderService readerService;
    private final BorrowService borrowService;

    @Autowired
    public DomainObjectValidator(final BookService bookService,
                                 final CopyService copyService,
                                 final ReaderService readerService,
                                 final BorrowService borrowService) {
        this.bookService = bookService;
        this.copyService = copyService;
        this.readerService = readerService;
        this.borrowService = borrowService;
    }

    public void validateBookId(final Long bookId) /*throws DomainObjectNotFoundException*/ {
        Optional.ofNullable(bookService.findOne(bookId))
                .orElseThrow(() -> new DomainObjectNotFoundException("Book", bookId));
        /*if (!bookService.exists(bookId)) { //which way is better?
            throw new DomainObjectNotFoundException("Book", bookId);
        }*/
    }

    public void validateCopyId(final Long copyId) {
        Optional.ofNullable(copyService.findOne(copyId))
                .orElseThrow(() -> new DomainObjectNotFoundException("Copy", copyId));
    }

    public void validateReaderId(final Long readerId) {
        Optional.ofNullable(readerService.findOne(readerId))
                .orElseThrow(() -> new DomainObjectNotFoundException("Reader", readerId));
    }

    public void validateBorrowId(final Long borrowId) {
        Optional.ofNullable(borrowService.findOne(borrowId))
                .orElseThrow(() -> new DomainObjectNotFoundException("Borrow", borrowId));
    }

    public void validateBook(final BookDto bookDto) {
        if (bookDto.getTitle().length() < 3) {
            throw new EntityConstraintViolationException("Book: Please enter book title between 3 and 255 characters.");
        }
        if (bookDto.getAuthor().length() < 3) {
            throw new EntityConstraintViolationException("Book: Please enter author name between 3 and 255 characters.");
        }
    }

    public void validateReader(final ReaderDto readerDto) {
        if (readerDto.getFirstname().length() < 3) {
            throw new EntityConstraintViolationException("Reader: Please enter reader firstname between 3 and 255 characters.");
        }
        if (readerDto.getLastname().length() < 3) {
            throw new EntityConstraintViolationException("Reader: please enter reader lastname between 3 and 255 characters.");
        }
    }

    public void validateCopy(final CopyDto copyDto) {
        if (copyDto.getBookId() == (null)) {
            throw new EntityConstraintViolationException("Copy: Book Id can not be null!");
        }
        validateBookId(copyDto.getBookId());
        if (Arrays.stream(Status.values()).noneMatch(status -> status.equals(copyDto.getStatus()))) {
            throw new EntityConstraintViolationException("Copy: wrong name of copy status!");
        }
    }

    public void validateBorrow(final BorrowDto borrowDto) {
        if (borrowDto.getCopyId() == (null)) {
            throw new EntityConstraintViolationException("Borrow: Copy Id can not be null!");
        }
        if (borrowDto.getReaderId() == (null)) {
            throw new EntityConstraintViolationException("Borrow: Reader Id can not be null!");
        }
        validateCopyId(borrowDto.getCopyId());
        validateReaderId(borrowDto.getReaderId());
    }
}