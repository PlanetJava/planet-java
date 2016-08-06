package net.planet.java.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/1/16.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
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

	@OneToMany(mappedBy = "feedSource", fetch = FetchType.LAZY)
	private List<RssFeed> rssFeeds = new ArrayList<>();

}
