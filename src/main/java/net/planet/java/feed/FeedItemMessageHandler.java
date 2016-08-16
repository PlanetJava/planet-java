package net.planet.java.feed;

import net.planet.java.domain.RssFeedItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import java.util.Date;
import java.util.List;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/8/16.
 */
public class FeedItemMessageHandler {
    private static final Logger log = LoggerFactory.getLogger(FeedItemMessageHandler.class);

    public void handleMessage(Message<List<RssFeedItem>> message) {
        log.info("At {} I received a message with feedid {} and payload {}",
                new Date(message.getHeaders().getTimestamp()).toString(),
                message.getHeaders().get("feedid", String.class),
                message.getPayload().toString());
    }
}
