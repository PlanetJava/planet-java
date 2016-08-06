package net.planet.java.service;

import net.planet.java.dto.FeedSourceDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
@Component
public interface FeedSourceService {

    List<FeedSourceDto> findAll();

    FeedSourceDto findOne(Long id);

    FeedSourceDto create(FeedSourceDto feedSourceDto);

    FeedSourceDto update(Long id, FeedSourceDto feedSourceDto);

    void deleteById(Long id);
}
