package com.rw.library.service;

import com.rw.library.domain.Borrow;
import com.rw.library.domain.Copy;
import com.rw.library.domain.Reader;
import com.rw.library.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Transactional
@Service
public class BorrowServiceImpl implements BorrowService {

    private BorrowRepository borrowRepository;
    private ReaderService readerService;

    @Autowired
    public void setBorrowRepository(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    @Autowired
    public void setReaderService(ReaderService readerService) {
        this.readerService = readerService;
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
    public Borrow saveOrUpdate(Borrow domainObject) {
        return ofNullable(borrowRepository.save(domainObject)).orElse(new Borrow());
    }

    @Override
    public void delete(Long id) {
        borrowRepository.delete(id);
    }

    public void returnBook(Long borrowId) {
        Borrow borrow = borrowRepository.findOne(borrowId);
        if (borrow.getReturnDate() == null) {
            borrow.setReturnDate(LocalDate.now());
            borrowRepository.save(borrow);
        }
    }

    @Override
    public List<Borrow> findAllByCopyAndReturnDateIsNull(Copy copy) {
        return new ArrayList<>(borrowRepository.findAllByCopyAndReturnDateIsNull(copy));
    }

    @Override
    public List<Borrow> findAllByReaderAndReturnDateIsNull(Reader reader) {
        return new ArrayList<>(borrowRepository.findAllByReaderAndReturnDateIsNull(reader));
    }

    @Override
    public List<Borrow> getBooksToReturn(final Long reader_id) {
        return new ArrayList<>(findAllByReaderAndReturnDateIsNull(readerService.getById(reader_id)));
    }
}
