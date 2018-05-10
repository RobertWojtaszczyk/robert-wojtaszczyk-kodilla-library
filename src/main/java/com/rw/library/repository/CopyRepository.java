package com.rw.library.repository;

import com.rw.library.domain.Book;
import com.rw.library.domain.Copy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CopyRepository extends CrudRepository<Copy, Long> {
    List<Copy> getListOfAvailableCopies(@Param("BOOK_ID") Long bookId);
    List<Copy> getListOfAvailableCopiesHQL(@Param("BOOK_ID") Long bookId);
    List<Copy> getByBook(Book book);
}
