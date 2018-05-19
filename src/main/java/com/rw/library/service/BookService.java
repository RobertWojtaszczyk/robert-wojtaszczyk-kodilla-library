package com.rw.library.service;

import com.rw.library.domain.Book;
import com.rw.library.domain.BookDto;

public interface BookService extends CRUDService<Book> {
    Book findByAuthor(String author);

    Book update(BookDto bookDto);
}
