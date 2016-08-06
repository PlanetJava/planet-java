package net.planet.java.resource;

import net.planet.java.dto.FeedSourceDto;
import org.springframework.hateoas.Resource;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */


public class FeedSourceResource extends Resource<FeedSourceDto> {

	public FeedSourceResource(FeedSourceDto content) {
		super(content);
	}
}
