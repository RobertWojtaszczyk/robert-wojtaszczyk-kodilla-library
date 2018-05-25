package com.rw.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BorrowedDto {
    private Long id;
    private String title;
    private String author;
    private String borrowDate;
    private String returnDate;
    private boolean overdue;
    private Long readerId;
}
