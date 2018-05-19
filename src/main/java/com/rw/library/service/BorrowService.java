package com.rw.library.service;

import com.rw.library.domain.Borrow;
import com.rw.library.domain.Copy;
import com.rw.library.domain.Reader;

import java.util.List;

public interface BorrowService extends CRUDService<Borrow> {
    void returnBook(Long borrowId);
    List<Borrow> findAllByCopyAndReturnDateIsNull(Copy copy);
    List<Borrow> findAllByReaderAndReturnDateIsNull(Reader reader);
    List<Borrow> getBooksToReturn(final Long reader_id);
}
