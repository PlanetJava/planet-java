package net.planet.java.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Bazlur Rahman Rokon
 * @since 7/31/16.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/", "home", "/webjars/**", "/css/**", "/js/**", "/images/**").permitAll()
			.antMatchers("/api/v1/**").permitAll() //TODO for now, will figure this out  later
			.anyRequest().authenticated();
	}
}
