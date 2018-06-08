package com.rw.library.service;

import com.rw.library.domain.Borrow;
import com.rw.library.domain.Copy;
import com.rw.library.domain.Reader;
import com.rw.library.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class BorrowServiceImpl implements BorrowService {
    private final BorrowRepository borrowRepository;

    @Autowired
    public BorrowServiceImpl(final BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    @Override
    public Borrow findOne(final Long id) {
        return borrowRepository.findOne(id);
    }

    @Override
    public List<Borrow> findAll() {
        List<Borrow> borrows = new ArrayList<>();
        borrowRepository.findAll().forEach(borrows::add);
        return borrows;
    }

    @Override
    public Page<Borrow> findAll(final Pageable pageable) {
        return borrowRepository.findAll(pageable);
    }

    @Override
    public Borrow save(final Borrow borrow) {
        return borrowRepository.save(borrow);
    }

    @Override
    public Borrow update(final Borrow updatedBorrow) {
        Borrow borrow = findOne(updatedBorrow.getId());
        borrow.setCopy(updatedBorrow.getCopy());
        borrow.setReader(updatedBorrow.getReader());
        return save(borrow);
    }

    @Override
    public void delete(final Long id) {
        borrowRepository.delete(id);
    }

    @Override
    public boolean exists(final Long id) {
        return borrowRepository.exists(id);
    }

    @Override
    public List<Borrow> findAllByCopyAndReturnDateIsNull(final Copy copy) {
        return new ArrayList<>(borrowRepository.findAllByCopyAndReturnDateIsNull(copy));
    }

    @Override
    public List<Borrow> getBorrowsForReader(final Reader reader) {
        return new ArrayList<>(borrowRepository.findAllByReaderAndReturnDateIsNull(reader));
    }

    public void returnBorrowedBook(final Long borrowId) {
        Borrow borrow = findOne(borrowId);
        if (borrow.getReturnDate() == null) {
            borrow.setReturnDate(LocalDate.now());
            save(borrow);
        }
    }
}
