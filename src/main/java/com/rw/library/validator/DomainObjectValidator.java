package com.rw.library.validator;

import com.rw.library.domain.*;
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

    public void validateBookId(final Long bookId) {
        if (!bookService.exists(bookId)) {
            throw new DomainObjectNotFoundException(Book.class.toString(), bookId);
        }
    }

    public void validateCopyId(final Long copyId) {
        if (!copyService.exists(copyId)) {
            throw new DomainObjectNotFoundException(Copy.class.toString(), copyId);
        }
    }

    public void validateReaderId(final Long readerId) {
        if (!readerService.exists(readerId)) {
            throw new DomainObjectNotFoundException(Reader.class.toString(), readerId);
        }
    }

    public void validateBorrowId(final Long borrowId) {
        if (!borrowService.exists(borrowId)) {
            throw new DomainObjectNotFoundException(Borrow.class.toString(), borrowId);
        }
    }

    public void validateCopy(final CopyDto copyDto) {
        if (copyDto.getBookId() == (null)) {
            throw new EntityConstraintViolationException(Copy.class.toString() + ": Book Id can not be null!");
        }
        validateBookId(copyDto.getBookId());
        if (Arrays.stream(Status.values()).noneMatch(status -> status.equals(copyDto.getStatus()))) {
            throw new EntityConstraintViolationException(Copy.class.toString() + ": wrong name of copy status!");
        }
    }

    public void validateBorrow(final BorrowDto borrowDto) {
        if (borrowDto.getCopyId() == (null)) {
            throw new EntityConstraintViolationException(Borrow.class.toString() + ": Copy Id can not be null!");
        }
        if (borrowDto.getReaderId() == (null)) {
            throw new EntityConstraintViolationException(Borrow.class.toString() + ": Reader Id can not be null!");
        }
        validateCopyId(borrowDto.getCopyId());
        validateReaderId(borrowDto.getReaderId());
    }
}