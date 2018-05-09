package com.rw.library.controller;

import com.rw.library.domain.*;
import com.rw.library.mapper.DomainMapper;
import com.rw.library.service.BookServiceImpl;
import com.rw.library.service.BorrowServiceImpl;
import com.rw.library.service.CopyServiceImpl;
import com.rw.library.service.ReaderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library")
public class LibraryController {
    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private CopyServiceImpl copyService;
    @Autowired
    private ReaderServiceImpl readerService;
    @Autowired
    private BorrowServiceImpl borrowService;
    @Autowired
    private DomainMapper domainMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/getAvailableCopies")
    public List<CopyDto> getAvailableCopies(@RequestParam Long book_id) {
        return domainMapper.mapToListAvailableCopies(book_id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAvailableCopiesSQL")
    public List<CopyDto> getAvailableCopiesSQL(@RequestParam Long book_id) {
        return domainMapper.mapToCopyDtoList(copyService.getListOfAvailableCopies(book_id));
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
        return domainMapper.mapToBookDto(bookService.saveOrUpdate(domainMapper.mapToBookForUpdate(bookDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateCopy", consumes = APPLICATION_JSON_VALUE)
    public CopyDto updateCopy(@RequestBody CopyDto copyDto) {
        return domainMapper.mapToCopyDto(copyService.saveOrUpdate(domainMapper.mapToCopyForUpdate(copyDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateReader", consumes = APPLICATION_JSON_VALUE)
    public ReaderDto updateReader(@RequestBody ReaderDto readerDto) {
        return domainMapper.mapToReaderDto(readerService.saveOrUpdate(domainMapper.mapToReaderForUpdate(readerDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateBorrow", consumes = APPLICATION_JSON_VALUE)
    public BorrowDto updateBorrow(@RequestBody BorrowDto borrowDto) {
        return domainMapper.mapToBorrowDto(borrowService.saveOrUpdate(domainMapper.mapToBorrowForUpdate(borrowDto)));
    }
}
