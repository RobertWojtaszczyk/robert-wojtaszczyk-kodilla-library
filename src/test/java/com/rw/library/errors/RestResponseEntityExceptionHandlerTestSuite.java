package com.rw.library.errors;

import com.rw.library.controller.LibraryController;
import com.rw.library.domain.BookDto;
import com.rw.library.mapper.DomainMapper;
import com.rw.library.service.BookServiceImpl;
import com.rw.library.service.BorrowServiceImpl;
import com.rw.library.service.CopyServiceImpl;
import com.rw.library.service.ReaderServiceImpl;
import com.rw.library.validator.DomainObjectValidator;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LibraryController.class)
public class RestResponseEntityExceptionHandlerTestSuite {
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
    private LibraryController libraryController;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldThrowEmptyResultDataAccessException() throws Exception {
        //Given
        when(libraryController.getBooks()).thenThrow(new EmptyResultDataAccessException(1));
        //When & Then
        mockMvc.perform(get("/v1/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldThrowEmptyResultDataAccessException2() {
        //Given
        thrown.expect(EmptyResultDataAccessException.class);
        when(libraryController.getBooks()).thenThrow(new EmptyResultDataAccessException(1));
        //When & Then
        libraryController.getBooks();
    }
}

