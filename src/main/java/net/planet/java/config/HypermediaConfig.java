package net.planet.java.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
@Configuration
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
public class HypermediaConfig  extends WebMvcConfigurerAdapter{
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        super.configureContentNegotiation(configurer);
    }
}
