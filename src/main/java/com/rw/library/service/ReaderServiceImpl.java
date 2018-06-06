package com.rw.library.service;

import com.rw.library.domain.Reader;
import com.rw.library.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Reader> listAll() {
        List<Reader> readers = new ArrayList<>();
        readerRepository.findAll().forEach(readers::add);
        return readers;
    }

    @Override
    public Reader getById(Long id) {
        return Optional.ofNullable(readerRepository.findOne(id)).orElse(new Reader());
    }

    @Override
    public Reader saveOrUpdate(Reader reader) {
        return Optional.ofNullable(readerRepository.save(reader)).orElse(new Reader());
    }

    @Override
    public Reader update(Reader updatedReader) {
        Reader reader = readerRepository.findOne(updatedReader.getId()); //nullPointerException if reader not exists
        if (!updatedReader.getFirstname().equals(reader.getFirstname())) {
            reader.setFirstname(Optional.ofNullable(updatedReader.getFirstname()).orElse(reader.getFirstname()));
        }
        if (!updatedReader.getLastname().equals(reader.getLastname())) {
            reader.setLastname(Optional.ofNullable(updatedReader.getLastname()).orElse(reader.getLastname()));
        }
        return Optional.ofNullable(readerRepository.save(reader)).orElse(new Reader());
    }

    @Override
    public void delete(Long id) {
        readerRepository.delete(id);
    }
}