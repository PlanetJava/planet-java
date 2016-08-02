package net.planet.java.repository;

import net.planet.java.domain.FeedSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class FeedSourceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FeedSourceRepository feedSourceRepository;

    @Test
    public void findAllFeedSource_ShouldReturnEmptyList() {
        List<FeedSource> sourceList = feedSourceRepository.findAll();
        assertThat(sourceList.size(), equalTo(0));
    }

    @Test
    public void givenOneFeedSource_findAllFeedSource_shouldReturnListWithSizeOne() {
        FeedSource feedSource = createNewFeedSource();

        entityManager.persist(feedSource);
        List<FeedSource> sourceList = feedSourceRepository.findAll();
        assertThat(sourceList.size(), equalTo(1));
    }

    private FeedSource createNewFeedSource() {
        LocalDateTime lastVisit = LocalDateTime.now();

        FeedSource feedSource = new FeedSource();
        feedSource.setSiteName("Bazlur's Steams of Thoughts");
        feedSource.setUrl("http://www.bazlur.com/feeds/posts/default?alt=rss");
        feedSource.setDescription("This blog is meant to be a vehicle for airing my interests, feelings and opinion");
        feedSource.setLastVisit(lastVisit);
        return feedSource;
    }
}