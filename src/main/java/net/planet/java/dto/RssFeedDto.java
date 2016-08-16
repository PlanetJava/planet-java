package net.planet.java.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/6/16.
 */
@Data
@EqualsAndHashCode(of = "id")
@Relation(value = "rssFeed", collectionRelation = "rssFeedItems")
public class RssFeedDto {
	private Long id;
	private String type;
	private String title;
	private String link;
	private String description;// the content
	private String guid;
	private String category;
	private String author;
	private LocalDateTime publishedDate;
}
