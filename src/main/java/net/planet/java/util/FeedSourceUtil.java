package net.planet.java.util;

import net.planet.java.domain.FeedSource;
import net.planet.java.dto.FeedSourceDto;

import java.time.LocalDateTime;

public class FeedSourceUtil {
    public FeedSourceUtil() {
    }

    public static FeedSource createNewFeedSource() {
        LocalDateTime lastVisit = LocalDateTime.now();

        FeedSource feedSource = new FeedSource();
        feedSource.setSiteName("Bazlur's Steams of Thoughts");
        feedSource.setUrl("http://www.bazlur.com/feeds/posts/default?alt=rss");
        feedSource.setDescription("This blog is meant to be a vehicle for airing my interests, feelings and opinion");
        feedSource.setLastVisit(lastVisit);

        return feedSource;
    }

    public static FeedSourceDto createNewFeedSourceDto() {
        LocalDateTime lastVisit = LocalDateTime.now();

        FeedSourceDto feedSourceDto = new FeedSourceDto();
        feedSourceDto.setSiteName("Bazlur's Steams of Thoughts");
        feedSourceDto.setUrl("http://www.bazlur.com/feeds/posts/default?alt=rss");
        feedSourceDto.setDescription("This blog is meant to be a vehicle for airing my interests, feelings and opinion");
        feedSourceDto.setLastVisit(lastVisit);

        return feedSourceDto;
    }

    public static FeedSource createFeedSource(String url, String name, String description) {
        FeedSource feedSource = new FeedSource();
        feedSource.setUrl(url);
        feedSource.setDescription(description);
        feedSource.setSiteName(name);
        feedSource.setLastVisit(LocalDateTime.now());

        return feedSource;
    }


}