package com.rw.library.mapper;

import com.rw.library.domain.*;
import com.rw.library.service.BookService;
import com.rw.library.service.BorrowService;
import com.rw.library.service.CopyService;
import com.rw.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DomainMapper {

    private final BookService bookService;
    private final CopyService copyService;
    private final ReaderService readerService;
    private final BorrowService borrowService;

    @Autowired
    public DomainMapper(final BookService bookService,
                        final CopyService copyService,
                        final ReaderService readerService,
                        final BorrowService borrowService) {
        this.bookService = bookService;
        this.copyService = copyService;
        this.readerService = readerService;
        this.borrowService = borrowService;
    }

    public Reader mapToReader(final ReaderDto readerDto) {
        return new Reader(
                readerDto.getId(),
                readerDto.getFirstname(),
                readerDto.getLastname()
        );
    }

    public Reader mapToUpdatedReader(final ReaderDto readerDto) {
        Reader reader = readerService.findOne(readerDto.getId());
        reader.setFirstname(readerDto.getFirstname());
        reader.setLastname(readerDto.getLastname());
        return reader;
    }

    public ReaderDto mapToReaderDto(final Reader reader) {
        return new ReaderDto(
                reader.getId(),
                reader.getDateCreated().toString(),
                reader.getLastUpdated().toString(),
                reader.getFirstname(),
                reader.getLastname(),
                reader.getBorrows().size(),
                borrowService.getBorrowsForReaderId(reader.getId()).size());
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
                LocalDate.now(),
                readerService.findOne(borrowDto.getReaderId()),
                copyService.findOne(borrowDto.getCopyId())
        );
    }

    public Borrow mapToUpdatedBorrow(final BorrowDto borrowDto) {
        Borrow borrow = borrowService.findOne(borrowDto.getId());
        borrow.setCopy(copyService.findOne(borrowDto.getCopyId()));
        borrow.setReader(readerService.findOne(borrowDto.getReaderId()));
        return borrow;
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

    public Borrow mapToBorrowReturnBookRequest(final Long borrowId) {
        Borrow borrow = borrowService.findOne(borrowId);
        if (borrow.getReturnDate() == null) {
            borrow.setReturnDate(LocalDate.now());
        }
        return borrow;
    }

    public Book mapToBook(final BookDto bookDto) {
        return new Book(
                bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getAuthor()
        );
    }

    public Book mapToUpdatedBook(final BookDto bookDto) {
        Book book = bookService.findOne(bookDto.getId());
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        return book;
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
        return new Copy(
                copyDto.getId(),
                copyDto.getStatus(),
                bookService.findOne(copyDto.getBookId())
        );
    }

    public Copy mapToUpdatedCopy(final CopyDto copyDto) {
        Copy copy = copyService.findOne(copyDto.getId());
        copy.setStatus(copyDto.getStatus());
        copy.setBook(bookService.findOne(copyDto.getBookId()));
        return copy;
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
        return copies.map(this::mapToCopyDto);
    }

    public BorrowedDto mapToBorrowedDto(final Borrow borrow) {
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
                .map(this::mapToBorrowedDto)
                .collect(Collectors.toList());
    }
}

