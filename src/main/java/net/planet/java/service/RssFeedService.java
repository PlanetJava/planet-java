package net.planet.java.service;

import net.planet.java.dto.RssFeedItemDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/6/16.
 */
@Component
public interface RssFeedService {
	PageImpl<RssFeedItemDto> findAll(Pageable pageable);

	RssFeedItemDto findOne(Long id);
}
