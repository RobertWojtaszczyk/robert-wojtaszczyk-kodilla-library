package com.rw.library.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "borrows")
public class Borrow extends AbstractDomainClass {

    public Borrow() {
    }

    public Borrow(Long id, LocalDate returnDate) {
        super(id);
        this.returnDate = returnDate;
    }

    public Borrow(Long id, LocalDateTime dateCreated, LocalDate borrowDate, LocalDate returnDate, Reader reader, Copy copy) {
        super(id, dateCreated);
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.reader = reader;
        this.copy = copy;
    }

    @Column(name = "borrowed_on")
    private LocalDate borrowDate;
    @Column(name = "returned_on")
    private LocalDate returnDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    private Reader reader;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "copy_id")
    private Copy copy;

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Copy getCopy() {
        return copy;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "id=" + getId() +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Borrow)) return false;
        Borrow borrow = (Borrow) o;
        return Objects.equals(getId(), borrow.getId()) &&
                Objects.equals(getBorrowDate(), borrow.getBorrowDate()) &&
                Objects.equals(getReturnDate(), borrow.getReturnDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBorrowDate());
    }
}
