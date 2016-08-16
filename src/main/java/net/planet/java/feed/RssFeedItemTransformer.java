package net.planet.java.feed;

import com.rometools.rome.feed.synd.SyndFeed;
import net.planet.java.domain.RssFeedItem;
import org.springframework.messaging.Message;

import java.util.List;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/8/16.
 */
public class RssFeedItemTransformer {
    public Message<List<RssFeedItem>> transform(Message<List<SyndFeed>> syndFeedMessage) {


        return null;
    }
}
