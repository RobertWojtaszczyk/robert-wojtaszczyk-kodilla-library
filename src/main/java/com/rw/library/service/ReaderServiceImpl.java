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
    public Reader findBySurname(String surname) {
        return Optional.ofNullable(readerRepository.findBySurname(surname)).orElse(new Reader());
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
        if (!updatedReader.getName().equals(reader.getName())) {
            reader.setName(Optional.ofNullable(updatedReader.getName()).orElse(reader.getName()));
        }
        if (!updatedReader.getSurname().equals(reader.getSurname())) {
            reader.setSurname(Optional.ofNullable(updatedReader.getSurname()).orElse(reader.getSurname()));
        }
        return Optional.ofNullable(readerRepository.save(reader)).orElse(new Reader());
    }

    @Override
    public void delete(Long id) {
        readerRepository.delete(id);
    }
}