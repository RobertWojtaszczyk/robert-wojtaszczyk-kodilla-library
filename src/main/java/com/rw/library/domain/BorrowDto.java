package com.rw.library.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BorrowDto {
    private Long id;
    private String dateCreated;
    private String lastUpdated;
    private String borrowDate;
    private String returnDate;
    private Long reader_id;
    private Long copy_id;
}