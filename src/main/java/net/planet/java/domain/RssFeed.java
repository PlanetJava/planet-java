package net.planet.java.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/6/16.
 */

//TODO: very basic design, we need to figure out what are the stuff we need to store

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class RssFeed extends PersistenceObject {
	private String type;
	private String title;
	private String link;

	@Column(columnDefinition = "text")
	private String description;// the content
	private String guid;
	private String category;
	private String author;
	private LocalDateTime publishedDate;

	@ManyToOne
	private FeedSource feedSource;
}

