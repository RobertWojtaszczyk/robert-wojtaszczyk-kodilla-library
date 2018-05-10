package com.rw.library.mapper;

import com.rw.library.domain.*;
import com.rw.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DomainMapper {

    @Autowired
    private BookService bookService;
    @Autowired
    private CopyService copyService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private BorrowService borrowService;

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

    public List<ReaderDto> mapToReadersDtoList(final List<?> readers) {
        return readers.stream()
                .map(o -> (Reader)o)
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
                getAvailableCopies(book.getId()).size()
        );
    }

    public List<BookDto> mapToBookDtoList(final List<?> books) {
        return books.stream()
                .map(o -> (Book)o)
                .map(this::mapToBookDto)
                .collect(Collectors.toList());
    }

    public CopyDto mapToCopyDto(final Copy copy) {
        return new CopyDto(
                copy.getId(),
                copy.getDateCreated().toString(),
                copy.getLastUpdated().toString(),
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

    private List<BorrowDto> getBooksToReturn(final Long reader_id) {
        return borrowService.findAllByReaderAndReturnDateIsNull(readerService.getById(reader_id)).stream()
                .map(this::mapToBorrowDto)
                .collect(Collectors.toList());
    }

    public List<CopyDto> getAvailableCopies(final Long bookId) {
        return copyService.getByBook(bookService.getById(bookId)).stream()
                .filter(copy -> copy.getBorrows().stream()
                      .allMatch(borrow -> borrow.getReturnDate() != null))
                    //.noneMatch(borrow -> borrow.getReturnDate() == null))
                .map(this::mapToCopyDto)
                .collect(Collectors.toList());
    }

    public List<CopyDto> getAvailableCopies2(final Long bookId) {
        return bookService.getById(bookId).getCopies().stream()
                .filter(copy -> borrowService.findAllByCopyAndReturnDateIsNull(copy).stream()
                        .noneMatch(borrow -> borrow.getCopy().getId().equals(copy.getId())))
                .map(this::mapToCopyDto)
                .collect(Collectors.toList());
    }
}