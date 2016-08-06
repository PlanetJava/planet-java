package net.planet.java.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
@Configuration
public class PlanetJavaAppConfig {

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }
}
