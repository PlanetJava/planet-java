package net.planet.java.service;

import net.planet.java.dto.RssFeedDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/6/16.
 */
@Component
public interface RssFeedService {
	PageImpl<RssFeedDto> findAll(Pageable pageable);

	RssFeedDto findOne(Long id);
}
