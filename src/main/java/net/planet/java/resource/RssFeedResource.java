package net.planet.java.resource;

import net.planet.java.dto.RssFeedDto;
import org.springframework.hateoas.Resource;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/6/16.
 */
public class RssFeedResource extends Resource<RssFeedDto> {

	public RssFeedResource(RssFeedDto content) {
		super(content);
	}
}
