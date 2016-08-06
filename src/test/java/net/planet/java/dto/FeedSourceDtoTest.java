package net.planet.java.dto;

import net.planet.java.domain.FeedSource;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
public class FeedSourceDtoTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenConvertFeedSourceEntityToFeedSourceDto_thenCorrect() {
        LocalDateTime lastVisit = LocalDateTime.now();

        FeedSource feedSource = new FeedSource();
        feedSource.setId((long) 1);
        feedSource.setSiteName("Bazlur's Steams of Thoughts");
        feedSource.setUrl("http://www.bazlur.com/feeds/posts/default?alt=rss");
        feedSource.setDescription("This blog is meant to be a vehicle for airing my interests, feelings and opinion");
        feedSource.setLastVisit(lastVisit);

        FeedSourceDto feedSourceDto = modelMapper.map(feedSource, FeedSourceDto.class);

        assertThat(feedSource.getId(), equalTo(feedSourceDto.getId()));
        assertThat(feedSource.getSiteName(), equalTo(feedSourceDto.getSiteName()));
        assertThat(feedSource.getDescription(), equalTo(feedSourceDto.getDescription()));
        assertThat(feedSource.getUrl(), equalTo(feedSourceDto.getUrl()));
        assertThat(feedSource.getLastVisit(), equalTo(feedSourceDto.getLastVisit()));
    }

    @Test
    public void whenConvertFeedSourceDtoToFeedSourceEntity_thenCorrect() {
        LocalDateTime lastVisit = LocalDateTime.now();

        FeedSourceDto feedSourceDto = new FeedSourceDto();
        feedSourceDto.setId((long) 1);
        feedSourceDto.setSiteName("Bazlur's Steams of Thoughts");
        feedSourceDto.setUrl("http://www.bazlur.com/feeds/posts/default?alt=rss");
        feedSourceDto.setDescription("This blog is meant to be a vehicle for airing my interests, feelings and opinion");
        feedSourceDto.setLastVisit(lastVisit);

        FeedSource feedSourceEntity = modelMapper.map(feedSourceDto, FeedSource.class);

        assertThat(feedSourceDto.getId(), equalTo(feedSourceEntity.getId()));
        assertThat(feedSourceDto.getSiteName(), equalTo(feedSourceEntity.getSiteName()));
        assertThat(feedSourceDto.getDescription(), equalTo(feedSourceEntity.getDescription()));
        assertThat(feedSourceDto.getUrl(), equalTo(feedSourceEntity.getUrl()));
        assertThat(feedSourceDto.getLastVisit(), equalTo(feedSourceEntity.getLastVisit()));
    }
}