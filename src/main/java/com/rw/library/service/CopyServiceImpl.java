package com.rw.library.service;

import com.rw.library.domain.Book;
import com.rw.library.domain.Copy;
import com.rw.library.domain.CopyDto;
import com.rw.library.repository.BookRepository;
import com.rw.library.repository.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Transactional
@Service
public class CopyServiceImpl implements CopyService {

    private CopyRepository copyRepository;
    private BookRepository bookRepository;

    @Autowired
    public void setCopyRepository(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
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
        return ofNullable(copyRepository.findOne(id)).orElse(new Copy());
    }

    @Override
    public Copy saveOrUpdate(Copy domainObject) {
        return ofNullable(copyRepository.save(domainObject)).orElse(new Copy());
    }

    @Override
    public Copy update(CopyDto copyDto) {
        Copy copy = copyRepository.findOne(copyDto.getId()); //nullPointerException if copy not exists
        copy.setStatus(copyDto.getStatus()); // if not in Enum: org.springframework.http.converter.HttpMessageNotReadableException
        copy.setBook(ofNullable(bookRepository.findOne(copyDto.getBookId())).orElse(copy.getBook()));  //nullPointerException if book not exists
        return ofNullable(copyRepository.save(copy)).orElse(new Copy());
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
