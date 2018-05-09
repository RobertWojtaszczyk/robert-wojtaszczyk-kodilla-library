package com.rw.library.service;

import com.rw.library.domain.Copy;
import com.rw.library.repository.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

//@Transactional
@Service
public class CopyServiceImpl implements CopyService {

    private CopyRepository copyRepository;

    @Autowired
    public void setCopyRepository(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    @Override
    public List<Copy> listAll() {
        List<Copy> copies = new ArrayList<>();
        copyRepository.findAll().forEach(copies::add);
        return copies;
    }

    @Override
    public Copy getById(Long id) {
        return copyRepository.findOne(id);
    }

    @Override
    public Copy saveOrUpdate(Copy domainObject) {
        return copyRepository.save(domainObject);
    }

    @Override
    public void delete(Long id) {
        copyRepository.delete(id);
    }

    @Override
    @Query
    public List<Copy> getListOfAvailableCopies(Long book_id) {
        return copyRepository.getListOfAvailableCopies(book_id);
    }
}
