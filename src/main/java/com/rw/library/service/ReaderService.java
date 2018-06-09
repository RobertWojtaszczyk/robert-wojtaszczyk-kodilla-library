package com.rw.library.service;

import com.rw.library.domain.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReaderService extends CRUDService<Reader> {
    Reader findOne(Long id);
    List<Reader> findAll();
    Page<Reader> findAll(Pageable pageable);
    Reader save(Reader domainObject);
    void delete(Long id);
    boolean exists(Long id);
}
