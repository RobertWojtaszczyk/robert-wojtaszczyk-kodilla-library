package com.rw.library.service;

import com.rw.library.domain.Book;
import com.rw.library.domain.Copy;
import com.rw.library.repository.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class CopyServiceImpl implements CopyService {

    private CopyRepository copyRepository;

    @Autowired
    public void setCopyRepository(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
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
    @Transactional
    public Copy saveOrUpdate(Copy domainObject) {
        return copyRepository.save(domainObject);
    }

    @Override
    @Transactional
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
    public List<Copy> getByBook(Book book) {
        return copyRepository.getByBook(book);
    }
}
