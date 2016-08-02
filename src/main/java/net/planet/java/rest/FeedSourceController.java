package net.planet.java.rest;

import net.planet.java.dto.FeedSourceDto;
import net.planet.java.service.FeedSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
@RestController
@RequestMapping("api/v1/feed-source")
public class FeedSourceController {

    private FeedSourceResourceAssembler feedSourceResourceAssembler;
    private FeedSourceService feedSourceService;

    @Autowired
    public FeedSourceController(FeedSourceResourceAssembler feedSourceResourceAssembler, FeedSourceService feedSourceService) {
        this.feedSourceResourceAssembler = feedSourceResourceAssembler;
        this.feedSourceService = feedSourceService;
    }

    @GetMapping
    public NestedContentResource<FeedSourceResource> findAll() {

        return new NestedContentResource<>(
                this.feedSourceResourceAssembler.toResources(this.feedSourceService.findAll()));
    }

    @GetMapping("/{id}")
    public Resource findOne(@PathVariable("id") Long id) {
        FeedSourceDto one = feedSourceService.findOne(id);

        return feedSourceResourceAssembler.toResource(one);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HttpHeaders create(@Valid @RequestBody FeedSourceDto feedSourceDto) {
        //TODO validate the bean first

        FeedSourceDto feedSourceDto1 = feedSourceService.create(feedSourceDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(FeedSourceController.class).slash(feedSourceDto1.getId()).toUri());

        return httpHeaders;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HttpHeaders update(@PathVariable("id") Long id, @Valid @RequestBody FeedSourceDto feedSourceDto) {
        //TODO validate the bean first

        FeedSourceDto update = feedSourceService.update(id, feedSourceDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(FeedSourceController.class).slash(update.getId()).toUri());

        return httpHeaders;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {

        feedSourceService.deleteById(id);
    }
}
