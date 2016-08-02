package net.planet.java;

import net.planet.java.domain.FeedSource;
import net.planet.java.repository.FeedSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
@Component
public class StartupHouseKeeper implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private FeedSourceRepository feedSourceRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<FeedSource> all = feedSourceRepository.findAll();
        if (all.isEmpty()) {
            //lets create some empty data
            createFeedSource("http://www.bazlur.com/feeds/posts/default?alt=rss", "Bazlur's Steams of Thoughts", "This blog is meant to be a vehicle for airing my interests, feelings and opinion");
            createFeedSource("http://www.bazlur.com/feeds/posts/default?alt=rss", "Bazlur's Steams of Thoughts", "This blog is meant to be a vehicle for airing my interests, feelings and opinion");
            createFeedSource("http://www.bazlur.com/feeds/posts/default?alt=rss", "Bazlur's Steams of Thoughts", "This blog is meant to be a vehicle for airing my interests, feelings and opinion");
            createFeedSource("http://www.bazlur.com/feeds/posts/default?alt=rss", "Bazlur's Steams of Thoughts", "This blog is meant to be a vehicle for airing my interests, feelings and opinion");
            createFeedSource("http://www.bazlur.com/feeds/posts/default?alt=rss", "Bazlur's Steams of Thoughts", "This blog is meant to be a vehicle for airing my interests, feelings and opinion");
            createFeedSource("http://www.bazlur.com/feeds/posts/default?alt=rss", "Bazlur's Steams of Thoughts", "This blog is meant to be a vehicle for airing my interests, feelings and opinion");
        }
    }

    private void createFeedSource(String url, String name, String description) {
        FeedSource feedSource = new FeedSource();
        feedSource.setUrl(url);
        feedSource.setDescription(description);
        feedSource.setSiteName(name);
        feedSource.setLastVisit(LocalDateTime.now());

        this.feedSourceRepository.save(feedSource);
    }
}
