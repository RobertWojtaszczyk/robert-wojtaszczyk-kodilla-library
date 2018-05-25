package com.rw.library.controller;

import com.rw.library.domain.*;
import com.rw.library.mapper.DomainMapper;
import com.rw.library.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @Autowired
    public LibraryController(BookService bookService, CopyService copyService, ReaderService readerService, BorrowService borrowService, DomainMapper domainMapper) {
        this.bookService = bookService;
        this.copyService = copyService;
        this.readerService = readerService;
        this.borrowService = borrowService;
        this.domainMapper = domainMapper;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAvailableCopies")
    public List<CopyDto> getAvailableCopies(@RequestParam Long book_id) {
        return domainMapper.mapToCopyDtoList(copyService.getAvailableCopies(book_id));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAvailableCopiesSQL")
    public List<CopyDto> getAvailableCopiesSQL(@RequestParam Long book_id) {
        return domainMapper.mapToCopyDtoList(copyService.getListOfAvailableCopies(book_id));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAvailableCopiesHQL")
    public List<CopyDto> getAvailableCopiesHQL(@RequestParam Long book_id) {
        return domainMapper.mapToCopyDtoList(copyService.getListOfAvailableCopiesHQL(book_id));
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

    @RequestMapping(method = RequestMethod.GET, value = "/getBooksToReturnByReader")
    public List<BorrowedDto> getBooksToReturnByReader(@RequestParam Long reader_id) {
        return borrowService.getBooksToReturn(reader_id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createBook", consumes = APPLICATION_JSON_VALUE)
    public void createBook(@RequestBody BookDto bookDto) {
        bookService.saveOrUpdate(domainMapper.mapToBook(bookDto));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createCopy", consumes = APPLICATION_JSON_VALUE)
    public void createCopy(@RequestBody CopyDto copyDto) {
        copyService.saveOrUpdate(domainMapper.mapToCopy(copyDto));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createReader", consumes = APPLICATION_JSON_VALUE)
    public void createReader(@RequestBody ReaderDto readerDto) {
        readerService.saveOrUpdate(domainMapper.mapToReader(readerDto));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createBorrow", consumes = APPLICATION_JSON_VALUE)
    public void createBorrow(@RequestBody BorrowDto borrowDto) {
        borrowService.saveOrUpdate(domainMapper.mapToBorrow(borrowDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateBook", consumes = APPLICATION_JSON_VALUE)
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        return domainMapper.mapToBookDto(bookService.update(bookDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateReader", consumes = APPLICATION_JSON_VALUE)
    public ReaderDto updateReader(@RequestBody ReaderDto readerDto) {
        return domainMapper.mapToReaderDto(readerService.update(readerDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateCopy", consumes = APPLICATION_JSON_VALUE)
    public CopyDto updateCopy(@RequestBody CopyDto copyDto) {
        return domainMapper.mapToCopyDto(copyService.update(copyDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/returnBook")
    public void returnBook(@RequestParam Long borrow_id) {
        borrowService.returnBook(borrow_id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getCopy")
    public CopyDto getCopy(@RequestParam Long copy_id) {
        return Optional.ofNullable(domainMapper.mapToCopyDto(copyService.getById(copy_id)))
                .orElseGet(() -> {
                    LOGGER.warn("Copy by id=" + copy_id + " not found!");
                    return new CopyDto();});
    }
}