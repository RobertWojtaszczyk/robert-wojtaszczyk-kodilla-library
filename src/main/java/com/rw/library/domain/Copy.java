package com.rw.library.domain;
import com.rw.library.domain.definitions.Status;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@NamedQuery(
        name = "Copy.getListOfAvailableCopiesHQL",
        query = "select c from Copy c left join c.borrows b with b.returnDate is null where c.book.id = :BOOK_ID and b.borrowDate is null"
)
@NamedNativeQuery(
        name = "Copy.getListOfAvailableCopies",
        query = "select c.* from copies c left join borrows b on c.id = b.copy_id and b.returned_on is null where c.book_id = :BOOK_ID and b.borrowed_on is null",
        resultClass = Copy.class
)

@Entity
@Table(name = "copies")
public class Copy extends AbstractDomainClass {

    public Copy() {
    }

    public Copy(Long id, Status status, Book book) {
        super(id);
        this.status = status;
        this.book = book;
    }

    public Copy(Long id, LocalDateTime dateCreated, Status status, Book book) {
        super(id, dateCreated);
        this.status = status;
        this.book = book;
    }
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;
    @OneToMany(
            targetEntity = Borrow.class,
            mappedBy = "copy",
            fetch = FetchType.LAZY
    )
    private List<Borrow> borrows = new LinkedList<>();

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(List<Borrow> borrows) {
        this.borrows = borrows;
    }

    @Override
    public String toString() {
        return "Copy{" +
                "id=" + getId() +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Copy copy = (Copy) o;
        return Objects.equals(getId(), copy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStatus());
    }
}
