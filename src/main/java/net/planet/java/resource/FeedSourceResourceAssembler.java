package net.planet.java.resource;

import net.planet.java.dto.FeedSourceDto;
import net.planet.java.rest.FeedSourceController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
@Component
public class FeedSourceResourceAssembler extends ResourceAssemblerSupport<FeedSourceDto, FeedSourceResource> {

	public FeedSourceResourceAssembler() {
		super(FeedSourceController.class, FeedSourceResource.class);
	}

	@Override
	public FeedSourceResource toResource(FeedSourceDto feedSourceDto) {

		return createResourceWithId(feedSourceDto.getId(), feedSourceDto);
	}

	@Override
	protected FeedSourceResource instantiateResource(FeedSourceDto feedSourceDto) {

		return new FeedSourceResource(feedSourceDto);
	}
}
