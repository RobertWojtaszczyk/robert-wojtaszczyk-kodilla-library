package com.rw.library.controller;

import com.rw.library.domain.*;
import com.rw.library.mapper.DomainMapper;
import com.rw.library.service.BookService;
import com.rw.library.service.BorrowService;
import com.rw.library.service.CopyService;
import com.rw.library.service.ReaderService;
import com.rw.library.validator.DomainObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library")
public class LibraryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LibraryController.class);

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
    public List<CopyDto> getAvailableCopies(@RequestParam Long book_id) {
        return domainMapper.mapToCopyDtoList(copyService.getAvailableCopies(book_id));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getBorrowedBooks")
    public List<BorrowedDto> getBorrowedBooks(@RequestParam Long reader_id) {
        return domainMapper.mapToBorrowedList(borrowService.getBorrowedBooks(domainMapper.mapReaderIdToReader(reader_id)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/returnBook")
    public void returnBook(@RequestParam Long borrow_id) {
        borrowService.returnBook(borrow_id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getBooks")
    public List<BookDto> getBooks() {
        return domainMapper.mapToBookDtoList(bookService.listAll());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getCopies")
    public List<CopyDto> getCopies() {
        return domainMapper.mapToCopyDtoList(copyService.listAll());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getReaders")
    public List<ReaderDto> getReaders() {
        return domainMapper.mapToReadersDtoList(readerService.listAll());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getBorrows")
    public List<BorrowDto> getBorrows() {
        return domainMapper.mapToBorrowsDtoList(borrowService.listAll());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createBook", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto createBook(@RequestBody BookDto bookDto) {
        return domainMapper.mapToBookDto(bookService.saveOrUpdate(domainMapper.mapToBook(bookDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createCopy", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CopyDto createCopy(@RequestBody CopyDto copyDto) {
        return domainMapper.mapToCopyDto(copyService.saveOrUpdate(domainMapper.mapToCopy(copyDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createReader", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReaderDto createReader(@RequestBody ReaderDto readerDto) {
        return domainMapper.mapToReaderDto(readerService.saveOrUpdate(domainMapper.mapToReader(readerDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createBorrow", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BorrowDto createBorrow(@RequestBody BorrowDto borrowDto) {
        return domainMapper.mapToBorrowDto(borrowService.saveOrUpdate(domainMapper.mapToBorrow(borrowDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateBook", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        domainObjectValidator.validateBook(bookDto);
        return domainMapper.mapToBookDto(bookService.update(domainMapper.mapToBook(bookDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateReader", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReaderDto updateReader(@RequestBody ReaderDto readerDto) {
        return domainMapper.mapToReaderDto(readerService.update(domainMapper.mapToReader(readerDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateCopy", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CopyDto updateCopy(@RequestBody CopyDto copyDto) {
        domainObjectValidator.validateCopyId(copyDto.getId());
        return domainMapper.mapToCopyDto(copyService.update(domainMapper.mapToCopy(copyDto)));
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
}