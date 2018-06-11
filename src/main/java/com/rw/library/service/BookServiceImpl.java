package com.rw.library.service;

import com.rw.library.domain.Book;
import com.rw.library.errors.DomainObjectNotFoundException;
import com.rw.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book findOne(final Long id) {
        return Optional.of(bookRepository.findOne(id))
                .orElseThrow(() -> new DomainObjectNotFoundException("Book", id));
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().forEach(books::add);
        return books;
    }

    @Override
    public Page<Book> findAll(final Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book save(final Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void delete(final Long id) {
        bookRepository.delete(id);
    }

    @Override
    public boolean exists(final Long id) {
        return bookRepository.exists(id);
    }
}