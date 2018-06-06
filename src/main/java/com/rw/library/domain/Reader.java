package com.rw.library.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "readers")
public class Reader extends AbstractDomainClass {

    public Reader() {
    }

    public Reader(Long id, LocalDateTime dateCreated, String name, String lastname) {
        super(id, dateCreated);
        this.firstname = name;
        this.lastname = lastname;
    }

    @Size(min = 3, max = 255, message = "Please enter firstname, between {min} and {max} characters.")
    private String firstname;
    @Size(min = 3, max = 255, message = "Please enter lastname, between {min} and {max} characters.")
    private String lastname;
    @OneToMany(
            targetEntity = Borrow.class,
            mappedBy = "reader",
            fetch = FetchType.LAZY
    )
    private List<Borrow> borrows = new LinkedList<>();

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(List<Borrow> borrows) {
        this.borrows = borrows;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + getId() +
                ", name='" + firstname + '\'' +
                ", surname='" + lastname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reader)) return false;
        Reader reader = (Reader) o;
        return Objects.equals(getId(), reader.getId()) &&
                Objects.equals(getFirstname(), reader.getFirstname()) &&
                Objects.equals(getLastname(), reader.getLastname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLastname());
    }
}
