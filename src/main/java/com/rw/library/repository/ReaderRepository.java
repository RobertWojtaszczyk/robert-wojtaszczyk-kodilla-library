package com.rw.library.repository;

import com.rw.library.domain.Reader;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReaderRepository extends PagingAndSortingRepository<Reader, Long> {
}