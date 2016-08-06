package net.planet.java.resource;

import net.planet.java.dto.FeedSourceDto;
import net.planet.java.rest.FeedSourceController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
	public List<FeedSourceResource> toResources(Iterable<? extends FeedSourceDto> entities) {
		List<FeedSourceResource> feedSourceResources = new ArrayList<>();

		for (FeedSourceDto iterable : entities) {
			FeedSourceResource resourceWithId = createResourceWithId(iterable.getId(), iterable);
			feedSourceResources.add(resourceWithId);
		}

		return feedSourceResources;
	}

	@Override
	protected FeedSourceResource instantiateResource(FeedSourceDto feedSourceDto) {

		return new FeedSourceResource(feedSourceDto);
	}
}
