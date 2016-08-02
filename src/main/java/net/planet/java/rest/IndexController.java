package net.planet.java.rest;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
@RestController
@RequestMapping("api/v1/")
public class IndexController {


    @RequestMapping(method = RequestMethod.GET)
    public ResourceSupport index() {
        ResourceSupport index = new ResourceSupport();
        index.add(linkTo(FeedSourceController.class).withRel("feedSources"));

        return index;
    }
}
