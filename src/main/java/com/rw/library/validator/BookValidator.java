package com.rw.library.validator;

import com.rw.library.domain.BookDto;
import com.rw.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BookValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookValidator.class);
    private final BookService bookService;

    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    public BookDto validateBook(final BookDto bookDto) {
        if ((bookDto.getId() != null) && !bookService.isValidId(bookDto.getId())) {
            LOGGER.info("Invalid Book id!");
            return new BookDto();
        }
        if (bookDto.getTitle().length() < 3) {
            LOGGER.info("Book title too short!");
        }
        if (bookDto.getAuthor().length() < 3) {
            LOGGER.info("Book author name too short!");
        }
        return bookDto;
    }

    public boolean validateId(final Long bookId) {
        if (bookService.isValidId(bookId)) {
            return true;
        }
        LOGGER.info("Invalid Book id!");
        return false;
    }
}
