package com.rw.library.mapper;

import com.rw.library.domain.*;
import com.rw.library.service.BookService;
import com.rw.library.service.BorrowService;
import com.rw.library.service.CopyService;
import com.rw.library.service.ReaderService;
import com.rw.library.validator.DomainObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DomainMapper {
    //    @Autowired
    private final BookService bookService;
    private final CopyService copyService;
    private final ReaderService readerService;
    private final BorrowService borrowService;
    private final DomainObjectValidator domainObjectValidator;

    @Autowired
    public DomainMapper(final BookService bookService, final CopyService copyService, final ReaderService readerService, final BorrowService borrowService, final DomainObjectValidator domainObjectValidator) {
        this.bookService = bookService;
        this.copyService = copyService;
        this.readerService = readerService;
        this.borrowService = borrowService;
        this.domainObjectValidator = domainObjectValidator;
    }

    public Reader mapToReader(final ReaderDto readerDto) {
        return new Reader(
                readerDto.getId(),
                null,
                readerDto.getFirstname(),
                readerDto.getLastname()
        );
    }

    public ReaderDto mapToReaderDto(final Reader reader) {
        return new ReaderDto(
                reader.getId(),
                reader.getDateCreated().toString(),
                reader.getLastUpdated().toString(),
                reader.getFirstname(),
                reader.getLastname(),
                reader.getBorrows().size(),
                borrowService.getBorrowedBooks(reader).size());
    }

    public List<ReaderDto> mapToReadersDtoList(final List<?> readers) {
        return readers.stream()
                .map(o -> (Reader)o)
                .map(this::mapToReaderDto)
                .collect(Collectors.toList());
    }

    public Reader mapReaderIdToReader(final Long readerId) {
        return readerService.getById(readerId);
    }

    public Borrow mapToBorrow(final BorrowDto borrowDto) {
        return new Borrow(
                borrowDto.getId(),
                null,
                LocalDate.now(),
                null,
                readerService.getById(borrowDto.getReader_id()),
                copyService.getById(borrowDto.getCopy_id())
        );
    }

    public BorrowDto mapToBorrowDto(final Borrow borrow) {
        String returnDate = borrow.getReturnDate() == null ? "" : borrow.getReturnDate().toString();
        return new BorrowDto(
                borrow.getId(),
                borrow.getDateCreated().toString(),
                borrow.getLastUpdated().toString(),
                borrow.getBorrowDate().toString(),
                returnDate,
                borrow.getReader().getId(),
                borrow.getCopy().getId()
        );
    }

    public List<BorrowDto> mapToBorrowsDtoList(final List<?> borrows) {
        return borrows.stream()
                .map(o -> (Borrow)o)
                .map(this::mapToBorrowDto)
                .collect(Collectors.toList());
    }

    public Book mapToBook(final BookDto bookDto) {
        return new Book(
                bookDto.getId(),
                null,
                bookDto.getTitle(),
                bookDto.getAuthor()
        );
    }

    public BookDto mapToBookDto(final Book book) {
        return new BookDto(
                book.getId(),
                book.getDateCreated().toString(),
                book.getLastUpdated().toString(),
                book.getTitle(),
                book.getAuthor(),
                book.getCopies().size(),
                copyService.getAvailableCopies(book.getId()).size()
        );
    }

    public List<BookDto> mapToBookDtoList(final List<?> books) {
        return books.stream()
                .map(o -> (Book)o)
                .map(this::mapToBookDto)
                .collect(Collectors.toList());
    }

    public Page<BookDto> mapToBookDtoPage(final Page<Book> books) {
        return books.map(this::mapToBookDto);
    }

    public Copy mapToCopy(final CopyDto copyDto) {
        Book book = bookService.getById(copyDto.getBookId());
        return new Copy(
                copyDto.getId(),
                null,
                copyDto.getStatus(),
                book
        );
    }

    public CopyDto mapToCopyDto(final Copy copy) {
        return new CopyDto(
                copy.getId(),
                copy.getDateCreated().toLocalDate().toString(),
                copy.getLastUpdated().toLocalDate().toString(),
                copy.getStatus(),
                copy.getBook().getId()
        );
    }

    public List<CopyDto> mapToCopyDtoList(final List<?> copies) {
        return copies.stream()
                .map(o -> (Copy)o)
                .map(this::mapToCopyDto)
                .collect(Collectors.toList());
    }

    public Page<CopyDto> mapToCopyDtoPage(final Page<Copy> copies) {
        return copies.map(new Converter<Copy, CopyDto>() {
            @Override
            public CopyDto convert(Copy source) {
                return mapToCopyDto(source);
            }
        });
    }

    public BorrowedDto mapToBorrowed(final Borrow borrow) {
         return new BorrowedDto(
                        borrow.getId(),
                        borrow.getCopy().getBook().getTitle(),
                        borrow.getCopy().getBook().getAuthor(),
                        borrow.getBorrowDate().toString(),
                        borrow.getBorrowDate().plusDays(30).toString(),
                        borrow.getBorrowDate().plusDays(30).isBefore(LocalDate.now()),
                        borrow.getReader().getId());
    }

    public List<BorrowedDto> mapToBorrowedList(final List<Borrow> borrows) {
        return borrows.stream()
                .map(this::mapToBorrowed)
                .collect(Collectors.toList());
    }
}