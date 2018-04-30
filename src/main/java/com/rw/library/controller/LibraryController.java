package com.rw.library.controller;

import com.rw.library.domain.Book;
import com.rw.library.domain.BookDTO;
import com.rw.library.domain.Copy;
import com.rw.library.mapper.BookMapper;
import com.rw.library.service.BookServiceImpl;
import com.rw.library.service.CopyServiceImpl;
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
    private BookMapper bookMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/getBooks")
    public List<Book> getBooks() {
        return bookService.listAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getBooksAndCopies")
    public List<BookDTO> getBooksWithCopies() {
        return bookMapper.mapToBookDtoList(bookService.listAll());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getCopies")
    public List<Copy> getCopies() {
        return copyService.listAll();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createBook", consumes = APPLICATION_JSON_VALUE)
    public void createBook(@RequestBody Book book) {
        bookService.saveOrUpdate(book);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createCopy", consumes = APPLICATION_JSON_VALUE)
    public void createCopy(@RequestBody Copy copy) {
        copyService.saveOrUpdate(copy);
    }
}
