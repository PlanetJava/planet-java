package net.planet.java.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Bazlur Rahman Rokon
 * @since 7/31/16.
 */
@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = {"/", "home"}, method = RequestMethod.GET)
    public String index() {
        log.debug("serving index page");

        return "index";
    }
}
