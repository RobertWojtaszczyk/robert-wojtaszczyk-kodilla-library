package com.rw.library.service;

import com.rw.library.domain.Reader;
import com.rw.library.repository.ReaderRepository;
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
public class ReaderServiceImpl implements ReaderService {
    private final ReaderRepository readerRepository;

    @Autowired
    public ReaderServiceImpl(final ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public Reader findOne(final Long id) {
        return readerRepository.findOne(id);
    }

    @Override
    public List<Reader> findAll() {
        List<Reader> readers = new ArrayList<>();
        readerRepository.findAll().forEach(readers::add);
        return readers;
    }

    @Override
    public Page<Reader> findAll(final Pageable pageable) {
        return readerRepository.findAll(pageable);
    }

    @Override
    public Reader save(final Reader reader) {
        return readerRepository.save(reader);
    }

    @Override
    public Reader update(final Reader updatedReader) {
        Reader reader = findOne(updatedReader.getId());
        if (!updatedReader.getFirstname().equals(reader.getFirstname())) {
            reader.setFirstname(Optional.ofNullable(updatedReader.getFirstname()).orElse(reader.getFirstname()));
        }
        if (!updatedReader.getLastname().equals(reader.getLastname())) {
            reader.setLastname(Optional.ofNullable(updatedReader.getLastname()).orElse(reader.getLastname()));
        }
        return save(reader);
    }

    @Override
    public void delete(final Long id) {
        readerRepository.delete(id);
    }

    @Override
    public boolean exists(final Long id) {
        return readerRepository.exists(id);
    }
}