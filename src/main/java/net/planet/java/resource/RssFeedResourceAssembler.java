package net.planet.java.resource;

import net.planet.java.dto.RssFeedDto;
import net.planet.java.rest.RssFeedController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/6/16.
 */
@Component
public class RssFeedResourceAssembler extends ResourceAssemblerSupport<RssFeedDto, RssFeedResource> {

	public RssFeedResourceAssembler() {
		super(RssFeedController.class, RssFeedResource.class);
	}

	@Override
	public RssFeedResource toResource(RssFeedDto rssFeedDto) {

		return createResourceWithId(rssFeedDto.getId(), rssFeedDto);
	}

	@Override
	protected RssFeedResource instantiateResource(RssFeedDto entity) {
		return new RssFeedResource(entity);

	}
}
