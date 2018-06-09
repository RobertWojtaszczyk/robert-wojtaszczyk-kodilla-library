package com.rw.library.service;

import com.rw.library.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService extends CRUDService<Book> {

    Book findOne(Long id);
    List<Book> findAll();
    Page<Book> findAll(Pageable pageable);
    Book save(Book book);
    void delete(Long id);
    boolean exists(Long id);
}
