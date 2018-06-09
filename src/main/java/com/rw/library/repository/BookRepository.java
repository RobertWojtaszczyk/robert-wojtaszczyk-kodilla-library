package com.rw.library.repository;

import com.rw.library.domain.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
}