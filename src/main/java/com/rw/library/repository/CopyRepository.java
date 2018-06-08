package com.rw.library.repository;

import com.rw.library.domain.Book;
import com.rw.library.domain.Copy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CopyRepository extends PagingAndSortingRepository<Copy, Long> {
    List<Copy> getAvailableCopies(@Param("BOOK_ID") Long bookId);
    List<Copy> getByBook(Book book);
    Page<Copy> findAllByBookId(Pageable pageable, Long bookId);
}
