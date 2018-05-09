package com.rw.library.service;

import com.rw.library.domain.Copy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CopyService extends CRUDService<Copy> {

    List<Copy> getListOfAvailableCopies(@Param("BOOK_ID") Long book_id);
}
