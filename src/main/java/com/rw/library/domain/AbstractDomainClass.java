package com.rw.library.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public class AbstractDomainClass implements DomainObject {

    public AbstractDomainClass() {
    }

    public AbstractDomainClass(Long id) {
        setId(id);
    }

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "created_on")
    private LocalDateTime dateCreated;
    @Column(name = "updated_on")
    private LocalDateTime lastUpdated;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        lastUpdated = LocalDateTime.now();
        if (dateCreated == null) {
            dateCreated = LocalDateTime.now();
        }
    }
}