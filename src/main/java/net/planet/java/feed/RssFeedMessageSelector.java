package net.planet.java.feed;

import com.rometools.rome.feed.synd.SyndFeed;
import net.planet.java.domain.FeedSource;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.dsl.support.tuple.Tuple2;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/6/16.
 */
public class RssFeedMessageSelector implements MessageSelector {

	@Override
	public boolean accept(Message<?> message) {
		List<Tuple2<FeedSource, SyndFeed>> payload = (List<Tuple2<FeedSource, SyndFeed>>) message.getPayload();

		//TODO filter
		return false;
	}
}
