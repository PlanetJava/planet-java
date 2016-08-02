package net.planet.java.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/1/16.
 */
@Entity
public class FeedSource extends PersistenceObject {
    //http://stackoverflow.com/questions/417142/what-is-the-maximum-length-of-a-url-in-different-browsers

    @Column(length = 2048, nullable = false)
    private String url;

    @Column(length = 500)
    private String siteName;

    @Column(length = 500)
    private String description;

    private Boolean expired = Boolean.FALSE;
    private Boolean deleted = Boolean.FALSE;
    private LocalDateTime lastVisit;

    public String getUrl() {
        return url;
    }

    public FeedSource setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getSiteName() {
        return siteName;
    }

    public FeedSource setSiteName(String siteName) {
        this.siteName = siteName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FeedSource setDescription(String description) {
        this.description = description;
        return this;
    }

    public Boolean getExpired() {
        return expired;
    }

    public FeedSource setExpired(Boolean expired) {
        this.expired = expired;
        return this;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public FeedSource setDeleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public FeedSource setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = lastVisit;
        return this;
    }
}
