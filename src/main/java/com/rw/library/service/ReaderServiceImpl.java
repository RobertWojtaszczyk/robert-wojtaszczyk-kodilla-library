package com.rw.library.service;

import com.rw.library.domain.Reader;
import com.rw.library.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReaderServiceImpl implements ReaderService {

    private ReaderRepository readerRepository;

    @Autowired
    public void setReaderRepository(ReaderRepository readerRepository) {
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
        return readerRepository.findOne(id);
    }

    @Override
    public Reader saveOrUpdate(Reader domainObject) {
        return readerRepository.save(domainObject);
    }

    @Override
    public void delete(Long id) {
        readerRepository.delete(id);
    }
}
