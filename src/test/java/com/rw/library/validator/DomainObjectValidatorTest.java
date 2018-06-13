package com.rw.library.validator;

import com.rw.library.domain.Book;
import com.rw.library.errors.DomainObjectNotFoundException;
import com.rw.library.service.BookServiceImpl;
import com.rw.library.service.BorrowServiceImpl;
import com.rw.library.service.CopyServiceImpl;
import com.rw.library.service.ReaderServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainObjectValidatorTest {
    @InjectMocks
    private DomainObjectValidator validator;

    @Mock
    private ReaderServiceImpl readerService;
    @Mock
    private BookServiceImpl bookService;
    @Mock
    private CopyServiceImpl copyService;
    @Mock
    private BorrowServiceImpl borrowService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void shouldThrowExceptionBookNotFound() {
        //Given
        thrown.expect(DomainObjectNotFoundException.class);
        when(bookService.exists(1L)).thenReturn(false);
        validator.validateBookId(1L);
        //Expect
//        Throwable thrown = catchThrowable(() -> { validator.validateBookId(1L).throwAnException() });
        assertThatThrownBy(() -> {throw new DomainObjectNotFoundException(Book.class.toString(), 1L);}).hasMessageContaining("Could not find");
        //When

    }
}


//Given

//When

//Then

//Given

//Expect

//When
