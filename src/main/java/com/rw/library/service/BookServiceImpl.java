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
import static java.util.Optional.ofNullable;

@Transactional
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book findByAuthor(String author) {
        return ofNullable(bookRepository.findByAuthor(author)).orElse(new Book());
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
    public Book getById(Long id) {
        return ofNullable(bookRepository.findOne(id)).orElse(new Book());
    }

    @Override
    public Book saveOrUpdate(final Book book) {
        return ofNullable(bookRepository.save(book)).orElse(new Book());
    }

    @Override
    public Book update(Book updatedBook) {
            Book book = bookRepository.findOne(updatedBook.getId()); //nullPointerException if book not exists
            if (!updatedBook.getAuthor().equals(book.getAuthor())) {
                book.setAuthor(ofNullable(updatedBook.getAuthor()).orElse(book.getAuthor()));
            }
            if (!updatedBook.getTitle().equals(book.getTitle())) {
                book.setTitle(ofNullable(updatedBook.getTitle()).orElse(book.getTitle()));
            }
            return ofNullable(bookRepository.save(book)).orElse(new Book());
    }

    @Override
    public void delete(Long id) {
        bookRepository.delete(id);
    }
}