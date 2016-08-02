package net.planet.java.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
public class FeedSourceDto {
    private Long id;

    @Size(max = 2048)
    @NotEmpty
    private String url;

    @Size(max = 200)
    private String siteName;

    @Size(max = 500)
    private String description;

    private LocalDateTime lastVisit;

    public Long getId() {
        return id;
    }

    public FeedSourceDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public FeedSourceDto setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getSiteName() {
        return siteName;
    }

    public FeedSourceDto setSiteName(String siteName) {
        this.siteName = siteName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FeedSourceDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public FeedSourceDto setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = lastVisit;
        return this;
    }
}
