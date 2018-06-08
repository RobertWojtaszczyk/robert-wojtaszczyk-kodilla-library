package com.rw.library.repository;

import com.rw.library.domain.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
}


   /*
    Optional<Book> findOne(Long id);
    List<Book> findAll();
    Page<Book> findAll(Pageable pageable);
    Book save(Book book);
    void delete(Long id);
    boolean exists(Long id);*/