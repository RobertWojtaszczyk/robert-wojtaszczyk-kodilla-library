package com.rw.library.controller;

import com.rw.library.domain.*;
import com.rw.library.mapper.DomainMapper;
import com.rw.library.service.BookService;
import com.rw.library.service.BorrowService;
import com.rw.library.service.CopyService;
import com.rw.library.service.ReaderService;
import com.rw.library.validator.DomainObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library")
public class LibraryController {

    private final BookService bookService;
    private final CopyService copyService;
    private final ReaderService readerService;
    private final BorrowService borrowService;
    private final DomainMapper domainMapper;
    private final DomainObjectValidator domainObjectValidator;

    @Autowired
    public LibraryController(final BookService bookService, final CopyService copyService, final ReaderService readerService, final BorrowService borrowService, final DomainMapper domainMapper, final DomainObjectValidator domainObjectValidator) {
        this.bookService = bookService;
        this.copyService = copyService;
        this.readerService = readerService;
        this.borrowService = borrowService;
        this.domainMapper = domainMapper;
        this.domainObjectValidator = domainObjectValidator;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAvailableCopies")
    public List<CopyDto> getAvailableCopies(@RequestParam Long bookId) {
        domainObjectValidator.validateBookId(bookId);
        return domainMapper.mapToCopyDtoList(copyService.getAvailableCopies(bookId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getBorrowedBooks")
    public List<BorrowedDto> getBorrowedBooks(@RequestParam Long readerId) {
        domainObjectValidator.validateReaderId(readerId);
        return domainMapper.mapToBorrowedList(borrowService.getBorrowsForReader(domainMapper.mapReaderIdToReader(readerId)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/returnBorrowedBook")
    public void returnBook(@RequestParam Long borrowId) {
        domainObjectValidator.validateBorrowId(borrowId);
        borrowService.returnBorrowedBook(borrowId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getBooks")
    public List<BookDto> getBooks() {
        return domainMapper.mapToBookDtoList(bookService.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getCopies")
    public List<CopyDto> getCopies() {
        return domainMapper.mapToCopyDtoList(copyService.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getReaders")
    public List<ReaderDto> getReaders() {
        return domainMapper.mapToReadersDtoList(readerService.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getBorrows")
    public List<BorrowDto> getBorrows() {
        return domainMapper.mapToBorrowsDtoList(borrowService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createBook", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto createBook(@RequestBody BookDto bookDto) {
        domainObjectValidator.validateBook(bookDto);
        return domainMapper.mapToBookDto(bookService.save(domainMapper.mapToBook(bookDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createCopy", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CopyDto createCopy(@RequestBody CopyDto copyDto) {
        domainObjectValidator.validateCopy(copyDto);
        return domainMapper.mapToCopyDto(copyService.save(domainMapper.mapToCopy(copyDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createReader", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReaderDto createReader(@RequestBody ReaderDto readerDto) {
        domainObjectValidator.validateReader(readerDto);
        return domainMapper.mapToReaderDto(readerService.save(domainMapper.mapToReader(readerDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createBorrow", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BorrowDto createBorrow(@RequestBody BorrowDto borrowDto) {
        domainObjectValidator.validateBorrow(borrowDto);
        return domainMapper.mapToBorrowDto(borrowService.save(domainMapper.mapToBorrow(borrowDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateBook", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        domainObjectValidator.validateBookId(bookDto.getId());
        domainObjectValidator.validateBook(bookDto);
        return domainMapper.mapToBookDto(bookService.save(domainMapper.mapToUpdatedBook(bookDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateReader", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReaderDto updateReader(@RequestBody ReaderDto readerDto) {
        domainObjectValidator.validateReaderId(readerDto.getId());
        domainObjectValidator.validateReader(readerDto);
        return domainMapper.mapToReaderDto(readerService.save(domainMapper.mapToUpdatedReader(readerDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateCopy", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CopyDto updateCopy(@RequestBody CopyDto copyDto) {
        domainObjectValidator.validateCopyId(copyDto.getId());
        domainObjectValidator.validateCopy(copyDto);
        return domainMapper.mapToCopyDto(copyService.save(domainMapper.mapToUpdatedCopy(copyDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateBorrow", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BorrowDto updateBorrow(@RequestBody BorrowDto borrowDto) {
        domainObjectValidator.validateBorrowId(borrowDto.getId());
        domainObjectValidator.validateBorrow(borrowDto);
        return domainMapper.mapToBorrowDto(borrowService.save(domainMapper.mapToUpdatedBorrow(borrowDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteBook")
    public void deleteBook(@RequestParam Long bookId) {
        domainObjectValidator.validateBookId(bookId);
        bookService.delete(bookId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteCopy")
    public void deleteCopy(@RequestParam Long copyId) {
        domainObjectValidator.validateCopyId(copyId);
        copyService.delete(copyId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteReader")
    public void deleteReader(@RequestParam Long readerId) {
        domainObjectValidator.validateReaderId(readerId);
        readerService.delete(readerId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteBorrow")
    public void deleteBorrow(@RequestParam Long borrowId) {
        domainObjectValidator.validateBorrowId(borrowId);
        borrowService.delete(borrowId);
    }
}