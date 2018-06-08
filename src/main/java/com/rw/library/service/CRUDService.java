package com.rw.library.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CRUDService<T> {
    T findOne(Long id);
    List<T> findAll();
    Page<T> findAll(Pageable pageable);
    T save(T domainObject);
    T update(T domainObject);
    void delete(Long id);
    boolean exists(Long id);
}
