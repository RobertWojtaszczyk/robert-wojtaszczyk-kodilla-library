package com.rw.library.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public class AbstractDomainClass implements DomainObject {

    public AbstractDomainClass() {
    }

    public AbstractDomainClass(Long id, LocalDateTime dateCreated) {
        setId(id);
        this.dateCreated = dateCreated;
    }

    @Id
    @GeneratedValue
    private Long id;
/*    @Version
    private Integer version;*/
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

/*    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }*/

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
