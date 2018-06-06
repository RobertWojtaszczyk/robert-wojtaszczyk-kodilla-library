package com.rw.library.service;

import com.rw.library.domain.Book;
import com.rw.library.domain.Copy;
import com.rw.library.repository.CopyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CopyServiceImpl implements CopyService {
    private final CopyRepository copyRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CopyServiceImpl.class);

    @Autowired
    public CopyServiceImpl(final CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    @Override
    public List<Copy> listAll() {
        List<Copy> copies = new ArrayList<>();
        copyRepository.findAll().forEach(copies::add);
        return copies;
    }

    @Override
    public Page<Copy> findAllByBook_Id(Pageable pageable, Long bookId) {
        return copyRepository.findAllByBook_Id(pageable, bookId);
    }

    @Override
    public Optional<Copy> findById(Long copyId) {
        return copyRepository.findById(copyId);
    }

    @Override
    public Copy getById(Long id) {
        return copyRepository.findOne(id);
    }

    @Override
    public Copy saveOrUpdate(Copy domainObject) {
        return Optional.ofNullable(copyRepository.save(domainObject)).orElseGet(() -> {
            LOGGER.error("Error due persisting Copy entity! method: Copy saveOrUpdate(Copy domainObject)");
            return new Copy();
        });
    }

    @Override
    public Copy update(Copy updatedCopy) {
        Copy copy = copyRepository.findOne(updatedCopy.getId());
        if (updatedCopy.getStatus() != null) {
            copy.setStatus(updatedCopy.getStatus());
        }
        if (updatedCopy.getBook() != null) {
            copy.setBook(updatedCopy.getBook());
        }
        return copyRepository.save(copy);
    }

    @Override
    public void delete(Long id) {
        copyRepository.delete(id);
    }

    @Override
    @Query
    public List<Copy> getAvailableCopies(Long bookId) {
        return copyRepository.getAvailableCopies(bookId);
    }

    @Override
    public List<Copy> getByBook(Book book) {
        return copyRepository.getByBook(book);
    }
}