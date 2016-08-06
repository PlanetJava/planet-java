package net.planet.java.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/1/16.
 */
@MappedSuperclass
public abstract class PersistenceObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    private LocalDateTime dateCreated;
    private LocalDateTime lastModifiedDate;

    @Column(length = 100)
    private String createdBy;

    @Column(length = 100)
    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public PersistenceObject setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getVersion() {
        return version;
    }

    public PersistenceObject setVersion(Long version) {
        this.version = version;
        return this;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public PersistenceObject setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public PersistenceObject setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PersistenceObject setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public PersistenceObject setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }
}
