package com.rw.library.domain;

import com.rw.library.domain.definitions.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CopyDto {
    private Long id;
    private String dateCreated;
    private String lastUpdated;
    private Status status;
    private Long bookId;
    private String title;
    private String author;
}
