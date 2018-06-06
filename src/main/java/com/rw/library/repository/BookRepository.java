package com.rw.library.repository;

import com.rw.library.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> /*CrudRepository<Book, Long>*/ {
    Optional<Book> findById(Long bookId);
    void delete(Long id);

    @Override
    Page<Book> findAll(Pageable pageable);
}
