package net.planet.java.repository;

import net.planet.java.domain.FeedSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/1/16.
 */
@Repository
public interface FeedSourceRepository extends JpaRepository<FeedSource, Long> {

    List<FeedSource> findAllByDeletedFalseAndExpiredFalse();

    Optional<FeedSource> findOneById(Long id);

    Optional<FeedSource> findOneByUrl(String url);
}
