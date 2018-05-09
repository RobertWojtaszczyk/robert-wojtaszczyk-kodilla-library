package com.rw.library.mapper;

import com.rw.library.domain.*;
import com.rw.library.service.BookServiceImpl;
import com.rw.library.service.BorrowServiceImpl;
import com.rw.library.service.CopyServiceImpl;
import com.rw.library.service.ReaderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DomainMapper {

    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private CopyServiceImpl copyService;
    @Autowired
    private ReaderServiceImpl readerService;
    @Autowired
    private BorrowServiceImpl borrowService;

    public Reader mapToReader(final ReaderDto readerDto) {
        return new Reader(
                readerDto.getId(),
                null,
                readerDto.getName(),
                readerDto.getSurname()
        );
    }

    public Reader mapToReaderForUpdate(final ReaderDto readerDto) {
        return new Reader(
                readerDto.getId(),
                readerService.getById(readerDto.getId()).getDateCreated(),
                readerDto.getName(),
                readerDto.getSurname()
        );
    }

    public ReaderDto mapToReaderDto(final Reader reader) {
        return new ReaderDto(
                reader.getId(),
                reader.getDateCreated().toString(),
                reader.getLastUpdated().toString(),
                reader.getName(),
                reader.getSurname(),
                reader.getBorrows().size(),
                getBooksToReturn(reader.getId()).size()
        );
    }

    public List<ReaderDto> mapToReadersDtoList(final List<Reader> readers) {
        return readers.stream()
                .map(this::mapToReaderDto)
                .collect(Collectors.toList());
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

    public Borrow mapToBorrowForUpdate(final BorrowDto borrowDto) {
        LocalDate returnDate = borrowDto.isReturning() ? LocalDate.now() : borrowService.getById(borrowDto.getId()).getReturnDate();
        return new Borrow(
                borrowDto.getId(),
                borrowService.getById(borrowDto.getId()).getDateCreated(),
                borrowService.getById(borrowDto.getId()).getBorrowDate(),
                returnDate,
                borrowService.getById(borrowDto.getId()).getReader(),
                borrowService.getById(borrowDto.getId()).getCopy()
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

    public List<BorrowDto> mapToBorrowsDtoList(final List<Borrow> borrows) {
        return borrows.stream()
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

    public Book mapToBookForUpdate(final BookDto bookDto) {
        return new Book(
                bookDto.getId(),
                bookService.getById(bookDto.getId()).getDateCreated(),
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
                mapToListAvailableCopies(book.getId()).size()
        );
    }

    public List<BookDto> mapToBookDtoList(final List<Book> books) {
        return books.stream()
                .map(this::mapToBookDto)
                .collect(Collectors.toList());
    }

    public CopyDto mapToCopyDto(final Copy copy) {
        return new CopyDto(
                copy.getId(),
                copy.getDateCreated().toString(),
                copy.getLastUpdated().toString(),
                copy.getStatus(),
                copy.getBook().getId(),
                copy.getBook().getTitle(),
                copy.getBook().getAuthor()
        );
    }

    public List<CopyDto> mapToCopyDtoList(final List<Copy> copies) {
        return copies.stream()
                .map(this::mapToCopyDto)
                .collect(Collectors.toList());
    }

    public Copy mapToCopy(final CopyDto copyDto) {
        return new Copy(
                copyDto.getId(),
                null,
                copyDto.getStatus(),
                bookService.getById(copyDto.getBookId())
        );
    }

    public Copy mapToCopyForUpdate(final CopyDto copyDto) {
        return new Copy(
                copyDto.getId(),
                copyService.getById(copyDto.getId()).getDateCreated(),
                copyDto.getStatus(),
                bookService.getById(copyDto.getBookId())
        );
    }

    public List<CopyDto> mapToListAvailableCopies(final Long book_id) {
        return bookService.getById(book_id).getCopies().stream()
                .filter(copy -> borrowService.findAllByCopyAndReturnDateIsNull(copy).stream()
                        .noneMatch(borrow -> borrow.getCopy().getId().equals(copy.getId())))
                .map(this::mapToCopyDto)
                .collect(Collectors.toList());
    }

    private List<BorrowDto> getBooksToReturn(final Long reader_id) {
        return borrowService.findAllByReaderAndReturnDateIsNull(readerService.getById(reader_id)).stream()
                .map(this::mapToBorrowDto)
                .collect(Collectors.toList());
    }
}