package com.rw.library.repository;

import com.rw.library.domain.Borrow;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BorrowRepository extends PagingAndSortingRepository<Borrow, Long> {
    List<Borrow> findAllByReaderIdAndReturnDateIsNull(Long id);
}
