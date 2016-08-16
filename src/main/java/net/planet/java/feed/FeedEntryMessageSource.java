package net.planet.java.feed;

import com.rometools.fetcher.FeedFetcher;
import com.rometools.fetcher.impl.HttpURLFeedFetcher;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.metadata.SimpleMetadataStore;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static net.planet.java.util.DateUtils.getLastModifiedDate;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/16/16.
 */
@SuppressWarnings("deprecation")
public class FeedEntryMessageSource extends IntegrationObjectSupport implements MessageSource<SyndEntry> {
    private static final Logger logger = LoggerFactory.getLogger(FeedEntryMessageSource.class);

    private final String metadataKey;
    private final URL feedUrl;
    private volatile MetadataStore metadataStore;
    private volatile long lastTime = -1;
    private volatile boolean initialized;
    private final Queue<SyndEntry> entries = new ConcurrentLinkedQueue<>();
    private final Object monitor = new Object();
    private final Comparator<SyndEntry> syndEntryComparator = new SyndEntryPublishedDateComparator();
    private final Object feedMonitor = new Object();
    private final FeedFetcher feedFetcher;

    public FeedEntryMessageSource(URL feedUrl, String metadataKey) {
        this(feedUrl, metadataKey,
                new HttpURLFeedFetcher(
                        com.rometools.fetcher.impl.HashMapFeedInfoCache.getInstance()));
    }

    public FeedEntryMessageSource(URL feedUrl, String metadataKey, FeedFetcher feedFetcher) {
        Assert.notNull(feedUrl, "feedUrl must not be null");
        Assert.notNull(metadataKey, "metadataKey must not be null");
        Assert.notNull(feedFetcher, "feedFetcher must not be null");
        this.feedUrl = feedUrl;
        this.metadataKey = metadataKey + "." + this.feedUrl;
        this.feedFetcher = feedFetcher;
    }

    public void setMetadataStore(MetadataStore metadataStore) {
        Assert.notNull(metadataStore, "metadataStore must not be null");
        this.metadataStore = metadataStore;
    }

    @Override
    public String getComponentType() {
        return "feed:inbound-channel-adapter";
    }

    @Override
    public Message<SyndEntry> receive() {
        Assert.isTrue(this.initialized, "'FeedEntryReaderMessageSource' must be initialized before it can produce Messages.");
        SyndEntry entry = doReceive();
        if (entry == null) {
            return null;
        }

        return this.getMessageBuilderFactory().withPayload(entry).build();
    }

    private SyndEntry doReceive() {
        SyndEntry nextEntry = null;
        synchronized (this.monitor) {
            nextEntry = getNextEntry();
            if (nextEntry == null) {
                // read feed and try again
                this.populateEntryList();
                nextEntry = getNextEntry();
            }
        }
        return nextEntry;
    }

    private SyndEntry getNextEntry() {
        SyndEntry next = this.entries.poll();
        if (next == null) {
            return null;
        }

        Date lastModifiedDate = getLastModifiedDate(next);
        if (lastModifiedDate != null) {
            this.lastTime = lastModifiedDate.getTime();
        } else {
            this.lastTime += 1; //NOSONAR - single poller thread
        }
        this.metadataStore.put(this.metadataKey, this.lastTime + "");
        return next;
    }

    private void populateEntryList() {
        SyndFeed syndFeed = this.getFeed();
        if (syndFeed != null) {
            List<SyndEntry> retrievedEntries = syndFeed.getEntries();
            if (!CollectionUtils.isEmpty(retrievedEntries)) {
                boolean withinNewEntries = false;
                Collections.sort(retrievedEntries, this.syndEntryComparator);
                for (SyndEntry entry : retrievedEntries) {
                    Date entryDate = getLastModifiedDate(entry);
                    if ((entryDate != null && entryDate.getTime() > this.lastTime)
                            || (entryDate == null && withinNewEntries)) {
                        this.entries.add(entry);
                        withinNewEntries = true;
                    }
                }
            }
        }
    }

    @Override
    protected void onInit() throws Exception {
        this.feedFetcher.addFetcherEventListener(new FeedQueueUpdatingFetcherListener());
        if (this.metadataStore == null) {
            // first try to look for a 'messageStore' in the context
            BeanFactory beanFactory = this.getBeanFactory();

            if (beanFactory != null) {
                this.metadataStore = IntegrationContextUtils.getMetadataStore(beanFactory);
            }
            // if no 'messageStore' in context, fall back to in-memory Map-based default
            if (this.metadataStore == null) {
                this.metadataStore = new SimpleMetadataStore();
            }
        }

        String lastTimeValue = this.metadataStore.get(this.metadataKey);
        if (StringUtils.hasText(lastTimeValue)) {
            this.lastTime = Long.parseLong(lastTimeValue);
        }
        this.initialized = true;
    }

    private SyndFeed getFeed() {
        SyndFeed feed;
        try {
            synchronized (this.feedMonitor) {
                feed = this.feedFetcher.retrieveFeed(this.feedUrl);
                logger.debug("retrieved feed at url '" + this.feedUrl + "'");

                if (feed == null) {
                    logger.debug("no feeds updated, returning null");
                }
            }
        } catch (Exception e) {
            throw new MessagingException(
                    "Failed to retrieve feed at url '" + this.feedUrl + "'", e);
        }
        return feed;
    }

    private class FeedQueueUpdatingFetcherListener implements com.rometools.fetcher.FetcherListener {

        @Override
        public void fetcherEvent(final com.rometools.fetcher.FetcherEvent event) {
            String eventType = event.getEventType();
            if (com.rometools.fetcher.FetcherEvent.EVENT_TYPE_FEED_POLLED.equals(eventType)) {
                logger.debug("\tEVENT: Feed Polled. URL = " + event.getUrlString());
            } else if (com.rometools.fetcher.FetcherEvent.EVENT_TYPE_FEED_UNCHANGED.equals(eventType)) {
                logger.debug("\tEVENT: Feed Unchanged. URL = " + event.getUrlString());
            }
        }
    }
}
