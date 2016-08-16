package net.planet.java.repository;

import net.planet.java.domain.RssFeedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/6/16.
 */
@Repository
public interface RssFeedRepository extends JpaRepository<RssFeedItem, Long> {

	Optional<RssFeedItem> findOneById(Long id);
}
