package com.rw.library.repository;

import com.rw.library.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> /*CrudRepository<Book, Long>*/ {
    Book findByAuthor(String author);
    void delete(Long id);

    @Override
    Page<Book> findAll(Pageable pageable);
}
