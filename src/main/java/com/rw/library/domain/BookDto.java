package com.rw.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {
    private Long id;
    private String dateCreated;
    private String lastUpdated;
    private String title;
    private String author;
    private int totalCopiesInLibrary;
    private int copiesAvailable;
}
