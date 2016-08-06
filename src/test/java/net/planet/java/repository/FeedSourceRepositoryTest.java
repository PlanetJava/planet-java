package net.planet.java.repository;

import net.planet.java.domain.FeedSource;
import net.planet.java.util.FeedSourceUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class FeedSourceRepositoryTest {
    // Unit testing convention:  http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FeedSourceRepository feedSourceRepository;

    @Test
    public void findAll_GivenEmptyData_ShouldReturnEmptyList() {
        List<FeedSource> sourceList = feedSourceRepository.findAll();
        assertThat(sourceList.size(), equalTo(0));
    }

    @Test
    public void findAll_GivenOneOneData_ShouldReturnListWithSizeOne() {
        FeedSource feedSource = FeedSourceUtil.createNewFeedSource();

        entityManager.persist(feedSource);
        List<FeedSource> sourceList = feedSourceRepository.findAll();
        assertThat(sourceList.size(), equalTo(1));
    }

    @Test
    public void findAllByDeletedFalseAndExpiredFalse_givenTwoValidData_shouldReturnListOfTwo() {
        FeedSource feedSource1 = FeedSourceUtil.createNewFeedSource();
        FeedSource feedSource2 = FeedSourceUtil.createNewFeedSource();
        FeedSource feedSource3 = FeedSourceUtil.createNewFeedSource();
        FeedSource feedSource4 = FeedSourceUtil.createNewFeedSource();

        entityManager.persist(feedSource1);
        entityManager.persist(feedSource2);
        entityManager.persist(feedSource3);
        entityManager.persist(feedSource4);

        feedSource2.setDeleted(true);
        feedSource3.setExpired(true);

        entityManager.merge(feedSource2);
        entityManager.merge(feedSource3);

        List<FeedSource> feedSourceList = feedSourceRepository.findAllByDeletedFalseAndExpiredFalse();
        assertThat(feedSourceList.size(), is(2));
    }

    @Test
    public void findOneById_givenEmpty_ShouldReturnNone() {
        clearTable();
        Optional<FeedSource> feedSource = feedSourceRepository.findOneById((long) 1);

        assertThat(feedSource.isPresent(), is(false));
    }

    @Test
    public void findOneById_givenOne_ShouldReturnOne() {
        clearTable();
        FeedSource newFeedSource = FeedSourceUtil.createNewFeedSource();
        entityManager.persist(newFeedSource);

        Optional<FeedSource> feedSource = feedSourceRepository.findOneById(newFeedSource.getId());

        assertThat(feedSource.isPresent(), is(true));
        assertThat(feedSource.get(), is(notNullValue()));
    }

    private void clearTable() {
        entityManager.getEntityManager().createQuery("delete from " + FeedSource.class.getSimpleName());
    }
}