package com.rw.library;

import com.rw.library.controller.LibraryController;
import com.rw.library.domain.Book;
import com.rw.library.domain.Copy;
import com.rw.library.domain.CopyDto;
import com.rw.library.domain.definitions.Status;
import com.rw.library.service.BookServiceImpl;
import com.rw.library.service.CopyServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LibraryApplicationTests {

    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private CopyServiceImpl copyService;
    @Autowired
    private LibraryController libraryController;

	@Test
	public void contextLoads() {
	}

    @Test
    public void testAddBookAndCopy() {

	    //Given
	    Book book1 = new Book();
        book1.setAuthor("Autor 1");
        book1.setTitle("Tytuł 1");
        book1 = bookService.save(book1);

        Copy copy1 = new Copy();
        copy1.setBook(book1);
        copy1.setStatus(Status.OK);
        copy1 = copyService.save(copy1);

        //When
        List<Book> books = bookService.findAll();
        List<Copy> copies = copyService.findAll();
        List<CopyDto> copyDtoList = libraryController.getCopies(); // org.hibernate.LazyInitializationException: could not initialize proxy - no Session

        //Then
        Assert.assertEquals(1, books.size());
        Assert.assertEquals(1, copies.size());
        Assert.assertEquals(1, copies.get(0).getBook().getId().intValue());

        copyService.delete(copy1.getId());
    }
}
