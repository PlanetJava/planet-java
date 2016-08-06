package net.planet.java;

import net.planet.java.domain.FeedSource;
import net.planet.java.repository.FeedSourceRepository;
import net.planet.java.util.FeedSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

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
		long count = feedSourceRepository.count();
		if (count == 0) {
			//test data, will be removed later
			FeedSource feedSource = FeedSourceUtil.createFeedSource("http://www.bazlur.com/feeds/posts/default?alt=rss", "Bazlur's Steams of Thoughts", "This blog is meant to be a vehicle for airing my interests, feelings and opinion");
			feedSourceRepository.save(feedSource);
		}
	}
}
