package com.rw.library.service;

import com.rw.library.domain.Book;
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
    public Optional<Book> findById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public List<Book> listAll() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().forEach(books::add);
        return books;
    }

    @Override
    public Page<Book> listAllPageable(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public boolean isValidId(final Long id) {
            return bookRepository.exists(id);
    }

    @Override
    public Book getById(Long id) {
        return Optional.ofNullable(bookRepository.findOne(id)).orElse(new Book());
    }

    @Override
    public Book saveOrUpdate(final Book book) {
        return Optional.ofNullable(bookRepository.save(book)).orElse(new Book());
    }

    @Override
    public Book update(Book updatedBook) {
            Book book = bookRepository.findOne(updatedBook.getId());
            book.setAuthor(updatedBook.getAuthor());
            book.setTitle(updatedBook.getTitle());
            return Optional.ofNullable(bookRepository.save(book)).orElse(new Book());
    }

    @Override
    public void delete(Long id) {
        bookRepository.delete(id);
    }
}