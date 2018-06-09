package com.rw.library.service;

import com.rw.library.domain.Book;
import com.rw.library.domain.Copy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CopyService extends CRUDService<Copy> {

    Copy findOne(Long id);
    List<Copy> findAll();
    Page<Copy> findAll(Pageable pageable);
    Copy save(Copy copy);
    void delete(Long id);
    boolean exists(Long id);

    List<Copy> getByBook(Book book);
    Page<Copy> findAllByBookId(Pageable pageable, Long bookId);
    List<Copy> getAvailableCopies(@Param("BOOK_ID") Long bookId);
}