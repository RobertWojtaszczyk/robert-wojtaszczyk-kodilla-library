package com.rw.library.controller;

import com.rw.library.domain.Book;
import com.rw.library.domain.BookDto;
import com.rw.library.mapper.DomainMapper;
import com.rw.library.service.*;
import com.rw.library.validator.DomainObjectValidator;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LibraryController.class)
public class LibraryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImpl bookService;
    @MockBean
    private CopyServiceImpl copyService;
    @MockBean
    private ReaderServiceImpl readerService;
    @MockBean
    private BorrowServiceImpl borrowService;
    @MockBean
    private DomainObjectValidator domainObjectValidator;
    @MockBean
    private DomainMapper domainMapper;

    @Ignore
    @Test
    public void shouldFetchTasksList() throws Exception {
        //Given
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Title");
        bookDto.setAuthor("Author name");
        bookDto.setTotalCopiesInLibrary(2);
        bookDto.setCopiesAvailable(1);
        List<BookDto> bookDtos = new ArrayList<>();
        bookDtos.add(bookDto);

        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Title", "Author name"));

        when(bookService.findAll()).thenReturn(books);
        when(domainMapper.mapToBookDtoList(books)).thenReturn(bookDtos);
        //When & Then
        mockMvc.perform(get("/v1/library/getBooks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Title")))
                .andExpect(jsonPath("$[0].author", is("Author name")))
                .andExpect(jsonPath("$[0].totalCopiesInLibrary", is(2)))
                .andExpect(jsonPath("$[0].copiesAvailable", is(1)));
    }
}
