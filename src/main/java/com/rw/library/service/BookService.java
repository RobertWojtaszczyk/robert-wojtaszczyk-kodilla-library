package com.rw.library.service;

import com.rw.library.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookService extends CRUDService<Book> {
    Optional<Book> findById(Long bookId);
    Book update(Book book);
    Page<Book> listAllPageable(Pageable pageable);
    boolean isValidId(final Long id);
    public void delete(Long id);
}
