package com.rw.library;

import com.rw.library.domain.Book;
import com.rw.library.domain.Copy;
import com.rw.library.domain.definitions.STATUS;
import com.rw.library.service.BookServiceImpl;
import com.rw.library.service.CopyServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryApplicationTests {

    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private CopyServiceImpl copyService;

	@Test
	public void contextLoads() {
	}

    @Test
    public void testAddBookAndCopy() {

	    //Given
	    Book book1 = new Book();
        book1.setAuthor("Autor 1");
        book1.setTitle("Tytuł 1");
        book1 = bookService.saveOrUpdate(book1);

        Copy copy1 = new Copy();
        copy1.setBook(book1);
        copy1.setStatus(STATUS.OK);
        copy1 = copyService.saveOrUpdate(copy1);

        //When
        List<Book> books = bookService.listAll();
        List<Copy> copies = copyService.listAll();

        //Then
//        Assert.assertEquals(1, books.size());
//        Assert.assertEquals(1, copies.size());

        books.forEach(System.out::println);
        copies.forEach(System.out::println);

        System.out.println(copy1.getBook());
//        System.out.println(copies.get(0).getBook().getId()); // org.hibernate.LazyInitializationException: could not initialize proxy - no Session
    }

}
