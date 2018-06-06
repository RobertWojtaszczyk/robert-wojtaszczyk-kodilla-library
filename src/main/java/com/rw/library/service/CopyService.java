package com.rw.library.service;

import com.rw.library.domain.Book;
import com.rw.library.domain.Copy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CopyService extends CRUDService<Copy> {
    List<Copy> getAvailableCopies(@Param("BOOK_ID") Long bookId);
    List<Copy> getByBook(Book book);
    Copy update(Copy copy);
    Page<Copy> findAllByBook_Id(Pageable pageable, Long bookId);
    Optional<Copy> findById(Long copyId);
}
