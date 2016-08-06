package net.planet.java.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
@Data
@EqualsAndHashCode(of = "id")
@Relation(value = "feedSource", collectionRelation = "feedSources")
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
}
