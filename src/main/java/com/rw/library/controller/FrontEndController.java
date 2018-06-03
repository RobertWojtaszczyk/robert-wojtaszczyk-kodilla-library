package com.rw.library.controller;

import com.rw.library.domain.Book;
import com.rw.library.domain.Paging.Pager;
import com.rw.library.mapper.DomainMapper;
import com.rw.library.repository.BookRepository;
import com.rw.library.service.BookService;
import com.rw.library.service.BorrowService;
import com.rw.library.service.CopyService;
import com.rw.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@CrossOrigin(origins = "*")
@Controller
public class FrontEndController {
    private final BookService bookService;
    private final CopyService copyService;
    private final ReaderService readerService;
    private final BorrowService borrowService;
    private final DomainMapper domainMapper;
    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final String INITIAL_PAGE_SIZE = "5";
    private static final int[] PAGE_SIZES = { 5, 10};

    @Autowired
    public FrontEndController(BookService bookService, CopyService copyService, ReaderService readerService, BorrowService borrowService, DomainMapper domainMapper) {
        this.bookService = bookService;
        this.copyService = copyService;
        this.readerService = readerService;
        this.borrowService = borrowService;
        this.domainMapper = domainMapper;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index(Model model){
//        model.addAttribute("readers", domainMapper.mapToReadersDtoList(readerService.listAll()));
//        model.addAttribute("books", domainMapper.mapToBookDtoList(bookService.listAll()));
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/books")
    public ModelAndView homepage(@RequestParam(name = "pageSize", defaultValue = INITIAL_PAGE_SIZE) int pageSize, @RequestParam(name = "page", defaultValue = "0") int page) {
        ModelAndView modelAndView = new ModelAndView("books");
        int evalPage = (page < 1) ? INITIAL_PAGE : page - 1;

        Page<Book> booksList = (bookService.listAllPageable(new PageRequest(evalPage, pageSize)));
        Pager pager = new Pager(booksList.getTotalPages(),booksList.getNumber(),BUTTONS_TO_SHOW);
        modelAndView.addObject("bookslist", booksList);
        modelAndView.addObject("selectedPageSize", pageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        return modelAndView;
    }
}