package net.planet.java.repository;

import net.planet.java.domain.FeedSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/1/16.
 */
@Repository
public interface FeedSourceRepository extends JpaRepository<FeedSource, Long> {

    Stream<FeedSource> findAllByDeletedFalseAndExpiredFalse();

    Stream<FeedSource> findAllByDeletedFalseAndExpiredFalse(Pageable pageable);
}
