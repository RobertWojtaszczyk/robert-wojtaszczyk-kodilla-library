package com.rw.library.service;

import com.rw.library.domain.Book;

public interface BookService extends CRUDService<Book> {
    Book findByAuthor(String author);
}
