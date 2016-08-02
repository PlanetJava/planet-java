package net.planet.java.rest;

import net.planet.java.dto.FeedSourceDto;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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
        FeedSourceResource sourceResource = createResourceWithId(feedSourceDto.getId(), feedSourceDto);
        sourceResource.add(linkTo(FeedSourceController.class).slash(feedSourceDto.getId()).withSelfRel());

        return sourceResource;
    }

    @Override
    protected FeedSourceResource instantiateResource(FeedSourceDto feedSourceDto) {

        return new FeedSourceResource(feedSourceDto);
    }
}
