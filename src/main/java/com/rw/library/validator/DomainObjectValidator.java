package com.rw.library.validator;

import com.rw.library.domain.BookDto;
import com.rw.library.errors.DomainObjectNotFoundException;
import com.rw.library.errors.EntityConstraintViolationException;
import com.rw.library.service.BookService;
import com.rw.library.service.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DomainObjectValidator {
    private final BookService bookService;
    private final CopyService copyService;

    @Autowired
    public DomainObjectValidator(final BookService bookService, final CopyService copyService) {
        this.bookService = bookService;
        this.copyService = copyService;
    }

    public void validateBook(final BookDto bookDto) {
        validateBookId(bookDto.getId());
        if (bookDto.getTitle().length() < 3) {
            throw new EntityConstraintViolationException("Book: Please enter book title between 3 and 255 characters.");
        }
        if (bookDto.getAuthor().length() < 3) {
            throw new EntityConstraintViolationException("Book: Please enter author name between 3 and 255 characters.");
        }
    }

    public void validateBookId(final Long bookId) {
        bookService.findById(bookId).orElseThrow(() -> new DomainObjectNotFoundException("Book", bookId));
    }

    public void validateCopyId(final Long copyId) {
        copyService.findById(copyId).orElseThrow(() -> new DomainObjectNotFoundException("Copy", copyId));
    }
}
