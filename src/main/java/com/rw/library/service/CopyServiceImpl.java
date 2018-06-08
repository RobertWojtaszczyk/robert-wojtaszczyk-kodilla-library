package com.rw.library.service;

import com.rw.library.domain.Book;
import com.rw.library.domain.Copy;
import com.rw.library.repository.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CopyServiceImpl implements CopyService {
    private final CopyRepository copyRepository;

    @Autowired
    public CopyServiceImpl(final CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    @Override
    public Copy findOne(final Long id) {
        return copyRepository.findOne(id);
    }

    @Override
    public List<Copy> findAll() {
//        return (List<Copy>) copyRepository.findAll();
//        return new ArrayList<>((Collection<? extends Copy>) copyRepository.findAll());
        List<Copy> copies = new ArrayList<>();
        copyRepository.findAll().forEach(copies::add);
        return copies;
    }

    @Override
    public Page<Copy> findAll(final Pageable pageable) {
        return copyRepository.findAll(pageable);
    }

    @Override
    public Copy save(final Copy copy) {
        return copyRepository.save(copy);
    }

    @Override
    public Copy update(final Copy updatedCopy) {
        Copy copy = findOne(updatedCopy.getId());
        if (updatedCopy.getStatus() != null) {
            copy.setStatus(updatedCopy.getStatus());
        }
        if (updatedCopy.getBook() != null) {
            copy.setBook(updatedCopy.getBook());
        }
        return save(copy);
    }

    @Override
    public void delete(final Long id) {
        copyRepository.delete(id);
    }

    @Override
    public boolean exists(final Long id) {
        return copyRepository.exists(id);
    }

    @Override
    public List<Copy> getByBook(final Book book) {
        return copyRepository.getByBook(book);
    }

    @Override
    public Page<Copy> findAllByBookId(final Pageable pageable, final Long bookId) {
        return copyRepository.findAllByBookId(pageable, bookId);
    }

    @Override
    @Query
    public List<Copy> getAvailableCopies(final Long bookId) {
        return copyRepository.getAvailableCopies(bookId);
    }
}