package com.rw.library.repository;

import com.rw.library.domain.Borrow;
import com.rw.library.domain.Copy;
import com.rw.library.domain.Reader;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BorrowRepository extends PagingAndSortingRepository<Borrow, Long> {
    List<Borrow> findAllByCopyAndReturnDateIsNull(Copy copy);
    List<Borrow> findAllByReaderAndReturnDateIsNull(Reader reader);
}
