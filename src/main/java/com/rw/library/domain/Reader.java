package com.rw.library.domain;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "readers")
public class Reader extends AbstractDomainClass {

    public Reader() {
    }

    public Reader(Long id, LocalDateTime dateCreated, String name, String surname) {
        super(id, dateCreated);
        this.name = name;
        this.surname = surname;
    }

    private String name;
    private String surname;
    @OneToMany(
            targetEntity = Borrow.class,
            mappedBy = "reader",
            fetch = FetchType.LAZY
    )
    private List<Borrow> borrows = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reader)) return false;
        Reader reader = (Reader) o;
        return Objects.equals(getId(), reader.getId()) &&
                Objects.equals(getName(), reader.getName()) &&
                Objects.equals(getSurname(), reader.getSurname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSurname());
    }
}
