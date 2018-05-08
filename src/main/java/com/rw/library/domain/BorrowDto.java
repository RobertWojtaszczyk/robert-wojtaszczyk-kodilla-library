package com.rw.library.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowDto {
    private Long id;
    private String dateCreated;
    private String lastUpdated;
    private String borrowDate;
    private String returnDate;
    private Long reader_id;
    private Long copy_id;
    private boolean returning;

    public BorrowDto() {
    }

    public BorrowDto(Long id, String dateCreated, String lastUpdated, String borrowDate, String returnDate, Long reader_id, Long copy_id, boolean returning) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.reader_id = reader_id;
        this.copy_id = copy_id;
        this.returning = returning;
    }

    public BorrowDto(Long id, String dateCreated, String lastUpdated, String borrowDate, String returnDate, Long reader_id, Long copy_id) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.reader_id = reader_id;
        this.copy_id = copy_id;
    }
}
