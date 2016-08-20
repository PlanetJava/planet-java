package net.planet.java.resource;

import net.planet.java.dto.RssFeedItemDto;
import net.planet.java.rest.RssFeedController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/6/16.
 */
@Component
public class RssFeedResourceAssembler extends ResourceAssemblerSupport<RssFeedItemDto, RssFeedResource> {

	public RssFeedResourceAssembler() {
		super(RssFeedController.class, RssFeedResource.class);
	}

	@Override
	public RssFeedResource toResource(RssFeedItemDto rssFeedItemDto) {

		return createResourceWithId(rssFeedItemDto.getId(), rssFeedItemDto);
	}

	@Override
	protected RssFeedResource instantiateResource(RssFeedItemDto entity) {

		return new RssFeedResource(entity);
	}
}
