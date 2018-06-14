package com.rw.library.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book extends AbstractDomainClass {

    public Book() {
    }

    public Book(Long id, String title, String author) {
        super(id);
        this.title = title;
        this.author = author;
    }

    @Size(min = 3, max = 255, message = "Please enter book title, between {min} and {max} characters.")
    private String title;
    @Size(min = 3, max = 255, message = "Please enter author name, between {min} and {max} characters.")
    private String author;
    @OneToMany(
            targetEntity = Copy.class,
            mappedBy = "book",
            fetch = FetchType.LAZY
    )
    private Set<Copy> copies = new HashSet<>();


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<Copy> getCopies() {
        return copies;
    }

    public void setCopies(Set<Copy> copies) {
        this.copies = copies;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(getId(), book.getId()) &&
                Objects.equals(getTitle(), book.getTitle()) &&
                Objects.equals(getAuthor(), book.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthor());
    }
}