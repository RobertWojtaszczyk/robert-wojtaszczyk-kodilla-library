package com.rw.library.service;

import com.rw.library.domain.Book;
import com.rw.library.domain.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService extends CRUDService<Book> {
    Book findByAuthor(String author);
    Book update(BookDto bookDto);
    Page<Book> listAllPageable(Pageable pageable);
}
