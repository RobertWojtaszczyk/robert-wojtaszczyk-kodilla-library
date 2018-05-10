package com.rw.library.service;

import com.rw.library.domain.Borrow;
import com.rw.library.domain.Copy;
import com.rw.library.domain.Reader;
import com.rw.library.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import static java.util.Optional.ofNullable;

@Service
public class BorrowServiceImpl implements BorrowService {

    private BorrowRepository borrowRepository;

    @Autowired
    public void setBorrowRepository(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    @Override
    public List<Borrow> listAll() {
        List<Borrow> borrows = new ArrayList<>();
        borrowRepository.findAll().forEach(borrows::add);
        return borrows;
    }

    @Override
    public Borrow getById(Long id) {
        return ofNullable(borrowRepository.findOne(id)).orElse(new Borrow());
    }

    @Override
    @Transactional
    public Borrow saveOrUpdate(Borrow domainObject) {
        return borrowRepository.save(domainObject);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        borrowRepository.delete(id);
    }

    @Override
    public List<Borrow> findAllByCopyAndReturnDateIsNull(Copy copy) {
        return new ArrayList<>(borrowRepository.findAllByCopyAndReturnDateIsNull(copy));
    }

    @Override
    public List<Borrow> findAllByReaderAndReturnDateIsNull(Reader reader) {
        List<Borrow> borrows = new ArrayList<>();
        borrowRepository.findAllByReaderAndReturnDateIsNull(reader).forEach(borrows::add);
        return borrows;
    }
}
