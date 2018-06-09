package com.rw.library.service;

import com.rw.library.domain.Borrow;
import com.rw.library.domain.Copy;
import com.rw.library.domain.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BorrowService extends CRUDService<Borrow> {
    Borrow findOne(Long id);
    List<Borrow> findAll();
    Page<Borrow> findAll(Pageable pageable);
    Borrow save(Borrow borrow);
    void delete(Long id);
    boolean exists(Long id);

    List<Borrow> findAllByCopyAndReturnDateIsNull(Copy copy);
    List<Borrow> getBorrowsForReader(Reader reader);
    void returnBorrowedBook(Long borrowId);
}
