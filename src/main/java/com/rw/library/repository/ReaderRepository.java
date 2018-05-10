package com.rw.library.repository;

import com.rw.library.domain.Reader;
import org.springframework.data.repository.CrudRepository;

public interface ReaderRepository extends CrudRepository<Reader, Long> {
    Reader findBySurname(String surname);
}