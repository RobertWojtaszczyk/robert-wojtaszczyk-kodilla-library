package com.rw.library.repository;

import com.rw.library.domain.Book;
import com.rw.library.domain.Copy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CopyRepository extends PagingAndSortingRepository<Copy, Long> {
    List<Copy> getAvailableCopies(@Param("BOOK_ID") Long bookId);
    List<Copy> getByBook(Book book);
    Page<Copy> findAllByBook_Id(Pageable pageable, Long bookId);
    Optional<Copy> findById(Long copyId);
}
