package com.rw.library.service;

import com.rw.library.domain.Reader;
import com.rw.library.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import static java.util.Optional.ofNullable;

@Service
public class ReaderServiceImpl implements ReaderService {

    private ReaderRepository readerRepository;

    @Autowired
    public void setReaderRepository(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public Reader findBySurname(String surname) {
        return ofNullable(readerRepository.findBySurname(surname)).orElse(new Reader());
    }

    @Override
    public List<Reader> listAll() {
        List<Reader> readers = new ArrayList<>();
        readerRepository.findAll().forEach(readers::add);
        return readers;
    }

    @Override
    public Reader getById(Long id) {
        return ofNullable(readerRepository.findOne(id)).orElse(new Reader());
    }

    @Override
    @Transactional
    public Reader saveOrUpdate(Reader domainObject) {
        return readerRepository.save(domainObject);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        readerRepository.delete(id);
    }
}
