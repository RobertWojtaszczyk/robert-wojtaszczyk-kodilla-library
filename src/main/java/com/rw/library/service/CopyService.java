package com.rw.library.service;

import com.rw.library.domain.Book;
import com.rw.library.domain.Copy;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CopyService extends CRUDService<Copy> {
    List<Copy> getAvailableCopies(@Param("BOOK_ID") Long bookId);
    List<Copy> getByBook(Book book);
    Copy update(Copy copy);
}
