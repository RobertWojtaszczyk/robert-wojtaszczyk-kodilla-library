package com.rw.library.repository;

import com.rw.library.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

    Book findByAuthor(String author);

    @Override
    Book save(Book book);
}
