package com.rw.library.service;

import com.rw.library.domain.Book;
import com.rw.library.domain.Copy;
import com.rw.library.domain.CopyDto;
import com.rw.library.repository.BookRepository;
import com.rw.library.repository.CopyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class CopyServiceImpl implements CopyService {
    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CopyServiceImpl.class);

    @Autowired
    public CopyServiceImpl(final CopyRepository copyRepository, final BookRepository bookRepository) {
        this.copyRepository = copyRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Copy> listAll() {
        List<Copy> copies = new ArrayList<>();
        copyRepository.findAll().forEach(copies::add);
        return copies;
    }

    @Override
    public Copy getById(Long id) {
        return Optional.ofNullable(copyRepository.findOne(id)).orElseGet(() -> {
            LOGGER.warn("Copy by id=" + id + " not found. Method: Copy getById(Long id)");
            return new Copy(); // null???
        });

    }

    @Override
    public Copy saveOrUpdate(Copy domainObject) {
        return Optional.ofNullable(copyRepository.save(domainObject)).orElseGet(() -> {
            LOGGER.error("Error due persisting Copy entity! method: Copy saveOrUpdate(Copy domainObject)");
            return new Copy();
        });
    }

    @Override // Poprawić, nie może tu być Dto!!! tylko Copy!!!
    public Copy update(CopyDto copyDto) {
        Copy copy = copyRepository.findOne(copyDto.getId()); //nullPointerException if copy not exists
        copy.setStatus(copyDto.getStatus()); // if not in Enum: org.springframework.http.converter.HttpMessageNotReadableException
        copy.setBook(Optional.ofNullable(bookRepository.findOne(copyDto.getBookId())).orElse(copy.getBook()));  //nullPointerException if book not exists
        return Optional.ofNullable(copyRepository.save(copy)).orElse(new Copy());
    }

    @Override
    public void delete(Long id) {
        copyRepository.delete(id);
    }

    @Override
    @Query(nativeQuery=true)
    public List<Copy> getListOfAvailableCopies(Long bookId) {
        return copyRepository.getListOfAvailableCopies(bookId);
    }

    @Override
    @Query
    public List<Copy> getListOfAvailableCopiesHQL(Long bookId) {
        return copyRepository.getListOfAvailableCopiesHQL(bookId);
    }

    @Override
    public List<Copy> getAvailableCopies(final Long bookId) {
        return getByBook(bookRepository.findOne(bookId)).stream()
                .filter(copy -> copy.getBorrows().stream()
                        .allMatch(borrow -> borrow.getReturnDate() != null))
                .collect(Collectors.toList());
    }

    @Override
    public List<Copy> getByBook(Book book) {
        return copyRepository.getByBook(book);
    }
}
