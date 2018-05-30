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
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = { 5, 10};


    public FrontEndController(BookService bookService, CopyService copyService, ReaderService readerService, BorrowService borrowService, DomainMapper domainMapper) {
        this.bookService = bookService;
        this.copyService = copyService;
        this.readerService = readerService;
        this.borrowService = borrowService;
        this.domainMapper = domainMapper;
    }

    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index(Model model){
//        model.addAttribute("readers", domainMapper.mapToReadersDtoList(readerService.listAll()));
//        model.addAttribute("books", domainMapper.mapToBookDtoList(bookService.listAll()));
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/books")
    public ModelAndView homepage(@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page) {
        //(@RequestParam(name = "pageSize", defaultValue = "1") int pageSize)
        ModelAndView modelAndView = new ModelAndView("books");

        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        //Page<Book> bookslist = bookRepository.findAll(new PageRequest(evalPage, evalPageSize));

        Page<Book> bookslist = (bookService.listAllPageable(new PageRequest(evalPage, evalPageSize)));
        Pager pager = new Pager(bookslist.getTotalPages(),bookslist.getNumber(),BUTTONS_TO_SHOW);
        modelAndView.addObject("bookslist",bookslist);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        return modelAndView;
    }
}