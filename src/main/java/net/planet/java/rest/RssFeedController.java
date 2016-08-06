package net.planet.java.rest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.planet.java.constants.ApiLinks;
import net.planet.java.dto.FeedSourceDto;
import net.planet.java.dto.RssFeedDto;
import net.planet.java.resource.FeedSourceResource;
import net.planet.java.resource.RssFeedResource;
import net.planet.java.resource.RssFeedResourceAssembler;
import net.planet.java.service.RssFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * We are
 *
 * @author Bazlur Rahman Rokon
 * @since 8/6/16.
 */
@Controller
@RestController
@RequestMapping(value = ApiLinks.API_RSS_FEED, produces = {MediaTypes.HAL_JSON_VALUE})
@ExposesResourceFor(FeedSourceDto.class)
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class RssFeedController {

	@NonNull
	private final RssFeedService rssFeedService;
	@NonNull
	private final RssFeedResourceAssembler rssFeedResourceAssembler;

	@GetMapping
	public PagedResources<RssFeedResource> findAll(Pageable pageable, PagedResourcesAssembler<RssFeedDto> pagedResourcesAssembler) {
		PageImpl<RssFeedDto> page = rssFeedService.findAll(pageable);

		return pagedResourcesAssembler.toResource(page, rssFeedResourceAssembler);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public RssFeedResource findOne(@PathVariable("id") Long id) {

		return rssFeedResourceAssembler.toResource(rssFeedService.findOne(id));
	}
}
