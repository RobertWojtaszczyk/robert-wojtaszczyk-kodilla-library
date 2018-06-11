package com.rw.library.service;

import com.rw.library.domain.Borrow;
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

    List<Borrow> getBorrowsForReaderId(Long readerId);
    void returnBorrowedBook(Long borrowId);
}
