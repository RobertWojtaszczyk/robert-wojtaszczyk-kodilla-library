package com.rw.library.repository;

import com.rw.library.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> /*CrudRepository<Book, Long>*/ {
    Book findByAuthor(String author);
}
