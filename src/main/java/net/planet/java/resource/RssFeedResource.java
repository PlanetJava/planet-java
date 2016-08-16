package net.planet.java.resource;

import net.planet.java.dto.RssFeedItemDto;
import org.springframework.hateoas.Resource;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/6/16.
 */
public class RssFeedResource extends Resource<RssFeedItemDto> {

	public RssFeedResource(RssFeedItemDto content) {
		super(content);
	}
}
