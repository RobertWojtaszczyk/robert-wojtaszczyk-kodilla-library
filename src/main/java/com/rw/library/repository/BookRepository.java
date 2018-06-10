package com.rw.library.repository;

import com.rw.library.domain.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
}