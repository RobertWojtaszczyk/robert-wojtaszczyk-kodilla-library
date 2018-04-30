package com.rw.library.service;

import com.rw.library.domain.Book;
import com.rw.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> listAll() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().forEach(books::add);
        return books;

    }

    @Override
    public Book getById(Long id) {
        return bookRepository.findOne(id);
    }

    @Override
    public Book saveOrUpdate(Book domainObject) {
        return bookRepository.save(domainObject);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        bookRepository.delete(id);
    }
}
