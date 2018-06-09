package com.rw.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BorrowDto {
    private Long id;
    private String dateCreated;
    private String lastUpdated;
    private String borrowDate;
    private String returnDate;
    private Long readerId;
    private Long copyId;
}