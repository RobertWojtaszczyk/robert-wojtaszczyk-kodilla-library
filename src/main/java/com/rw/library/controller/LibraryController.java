package com.rw.library.controller;

import com.rw.library.domain.*;
import com.rw.library.mapper.DomainMapper;
import com.rw.library.service.BookService;
import com.rw.library.service.BorrowService;
import com.rw.library.service.CopyService;
import com.rw.library.service.ReaderService;
import com.rw.library.validator.DomainObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class LibraryController {

    private final BookService bookService;
    private final CopyService copyService;
    private final ReaderService readerService;
    private final BorrowService borrowService;
    private final DomainMapper domainMapper;
    private final DomainObjectValidator domainObjectValidator;

    @Autowired
    public LibraryController(final BookService bookService,
                             final CopyService copyService,
                             final ReaderService readerService,
                             final BorrowService borrowService,
                             final DomainMapper domainMapper,
                             final DomainObjectValidator domainObjectValidator) {
        this.bookService = bookService;
        this.copyService = copyService;
        this.readerService = readerService;
        this.borrowService = borrowService;
        this.domainMapper = domainMapper;
        this.domainObjectValidator = domainObjectValidator;
    }

    @GetMapping(value = "/books/{bookId}/availableCopies")
    public List<CopyDto> getAvailableCopies(@PathVariable Long bookId) {
        domainObjectValidator.validateBookId(bookId);
        return domainMapper.mapToCopyDtoList(copyService.getAvailableCopies(bookId));
    }

    @GetMapping(value = "/readers/{readerId}/borrowedBooks")
    public List<BorrowedDto> getBorrowedBooks(@PathVariable Long readerId) {
        domainObjectValidator.validateReaderId(readerId);
        return domainMapper.mapToBorrowedList(borrowService.getBorrowsForReaderId(readerId));
    }

    @PutMapping(value = "/borrows/{borrowId}/returnBook")
    public void returnBook(@PathVariable Long borrowId) {
        domainObjectValidator.validateBorrowId(borrowId);
        borrowService.save(domainMapper.mapToBorrowReturnBookRequest(borrowId));
    }

    @GetMapping(value = "/books")
    public List<BookDto> getBooks() {
        return domainMapper.mapToBookDtoList(bookService.findAll());
    }

    @GetMapping(value = "/books", params = {"page", "size"})
    public Page<BookDto> getBooksPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        return domainMapper.mapToBookDtoPage(bookService.findAll(new PageRequest(page, size)));
    }

    @PostMapping(value = "/books", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto createBook(@RequestBody BookDto bookDto) {
        return domainMapper.mapToBookDto(bookService.save(domainMapper.mapToBook(bookDto)));
    }

    @PutMapping(value = "/books", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        domainObjectValidator.validateBookId(bookDto.getId());
        return domainMapper.mapToBookDto(bookService.save(domainMapper.mapToUpdatedBook(bookDto)));
    }

    @DeleteMapping(value = "/books/{bookId}")
    public void deleteBook(@PathVariable Long bookId) {
        domainObjectValidator.validateBookId(bookId);
        bookService.delete(bookId);
    }

    @GetMapping(value = "/copies")
    public List<CopyDto> getCopies() {
        return domainMapper.mapToCopyDtoList(copyService.findAll());
    }

    @GetMapping(value = "/copiesPage", params = {"page", "size"})
    public Page<CopyDto> getCopiesPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        return domainMapper.mapToCopyDtoPage(copyService.findAll(new PageRequest(page, size)));
    }

    @PostMapping(value = "/copies", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CopyDto createCopy(@RequestBody CopyDto copyDto) {
        domainObjectValidator.validateCopy(copyDto);
        return domainMapper.mapToCopyDto(copyService.save(domainMapper.mapToCopy(copyDto)));
    }

    @PutMapping(value = "/copies", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CopyDto updateCopy(@RequestBody CopyDto copyDto) {
        domainObjectValidator.validateCopyId(copyDto.getId());
        domainObjectValidator.validateCopy(copyDto);
        return domainMapper.mapToCopyDto(copyService.save(domainMapper.mapToUpdatedCopy(copyDto)));
    }

    @DeleteMapping(value = "/copies/{copyId}")
    public void deleteCopy(@PathVariable Long copyId) {
        domainObjectValidator.validateCopyId(copyId);
        copyService.delete(copyId);
    }

    @GetMapping(value = "/readers")
    public List<ReaderDto> getReaders() {
        return domainMapper.mapToReadersDtoList(readerService.findAll());
    }

    @PostMapping(value = "/readers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReaderDto createReader(@RequestBody ReaderDto readerDto) {
        return domainMapper.mapToReaderDto(readerService.save(domainMapper.mapToReader(readerDto)));
    }

    @PutMapping(value = "/readers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReaderDto updateReader(@RequestBody ReaderDto readerDto) {
        domainObjectValidator.validateReaderId(readerDto.getId());
        return domainMapper.mapToReaderDto(readerService.save(domainMapper.mapToUpdatedReader(readerDto)));
    }

    @DeleteMapping(value = "/readers/{readerId}")
    public void deleteReader(@PathVariable Long readerId) {
        domainObjectValidator.validateReaderId(readerId);
        readerService.delete(readerId);
    }

    @GetMapping(value = "/borrows")
    public List<BorrowDto> getBorrows() {
        return domainMapper.mapToBorrowsDtoList(borrowService.findAll());
    }

    @PostMapping(value = "/borrows", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BorrowDto createBorrow(@RequestBody BorrowDto borrowDto) {
        domainObjectValidator.validateBorrow(borrowDto);
        return domainMapper.mapToBorrowDto(borrowService.save(domainMapper.mapToBorrow(borrowDto)));
    }

    @PutMapping(value = "/borrows", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BorrowDto updateBorrow(@RequestBody BorrowDto borrowDto) {
        domainObjectValidator.validateBorrowId(borrowDto.getId());
        domainObjectValidator.validateBorrow(borrowDto);
        return domainMapper.mapToBorrowDto(borrowService.save(domainMapper.mapToUpdatedBorrow(borrowDto)));
    }

    @DeleteMapping(value = "/borrows/{borrowId}")
    public void deleteBorrow(@PathVariable Long borrowId) {
        domainObjectValidator.validateBorrowId(borrowId);
        borrowService.delete(borrowId);
    }
}