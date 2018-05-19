package com.rw.library.service;

import com.rw.library.domain.Reader;
import com.rw.library.domain.ReaderDto;

public interface ReaderService extends CRUDService<Reader> {
    Reader findBySurname(String surname);

    Reader update(ReaderDto readerDto);
}
