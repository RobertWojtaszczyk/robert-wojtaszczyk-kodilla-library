package com.rw.library.service;

import com.rw.library.domain.Reader;

public interface ReaderService extends CRUDService<Reader> {
    Reader findBySurname(String surname);
    Reader update(Reader reader);
}
