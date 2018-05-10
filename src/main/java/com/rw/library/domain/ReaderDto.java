package com.rw.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReaderDto {
    private Long id;
    private String dateCreated;
    private String lastUpdated;
    private String name;
    private String surname;
    private int totalBorrowedBooks;
    private int currentlyBorrowedBooks;
}