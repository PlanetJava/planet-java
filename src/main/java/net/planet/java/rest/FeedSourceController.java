package net.planet.java.rest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.planet.java.constants.ApiLinks;
import net.planet.java.dto.FeedSourceDto;
import net.planet.java.resource.FeedSourceResource;
import net.planet.java.resource.FeedSourceResourceAssembler;
import net.planet.java.service.FeedSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
@RestController
@RequestMapping(value = ApiLinks.API_FEED_SOURCE, produces = {MediaTypes.HAL_JSON_VALUE})
@ExposesResourceFor(FeedSourceDto.class)
@RequiredArgsConstructor(onConstructor = @_(@Autowired))

public class FeedSourceController {

	@NonNull
	private final FeedSourceResourceAssembler feedSourceResourceAssembler;

	@NonNull
	private final FeedSourceService feedSourceService;

	@GetMapping
	public Resources<FeedSourceResource> findAll() {
		List<FeedSourceResource> feedSourceResources = feedSourceResourceAssembler.toResources(this.feedSourceService.findAll());

		return new Resources<>(feedSourceResources);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public FeedSourceResource findOne(@PathVariable("id") Long id) {

		return feedSourceResourceAssembler.toResource(feedSourceService.findOne(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FeedSourceResource create(@Valid @RequestBody FeedSourceDto feedSourceDto) {
		FeedSourceDto feedSourceDtoSaved = feedSourceService.create(feedSourceDto);

        return feedSourceResourceAssembler.toResource(feedSourceDto);

		/*
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(linkTo(FeedSourceController.class).slash(feedSourceDtoSaved.getId()).toUri());

		return httpHeaders; */
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public HttpHeaders update(@PathVariable("id") Long id, @Valid @RequestBody FeedSourceDto feedSourceDto) {
		FeedSourceDto updated = feedSourceService.update(id, feedSourceDto);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(linkTo(FeedSourceController.class).slash(updated.getId()).toUri());

		return httpHeaders;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") Long id) {

		feedSourceService.deleteById(id);
	}
}
