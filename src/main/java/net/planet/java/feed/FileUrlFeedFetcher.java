package net.planet.java.feed;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.modelmapper.internal.util.Assert;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/16/16.
 */
@SuppressWarnings("deprecation")
public class FileUrlFeedFetcher extends com.rometools.fetcher.impl.AbstractFeedFetcher {

    @Override
    public SyndFeed retrieveFeed(URL feedUrl)
            throws IOException, FeedException, com.rometools.fetcher.FetcherException {
        Assert.notNull(feedUrl, "feedUrl must not be null");
        URLConnection connection = feedUrl.openConnection();
        com.rometools.fetcher.impl.SyndFeedInfo syndFeedInfo = new com.rometools.fetcher.impl.SyndFeedInfo();
        this.refreshFeedInfo(feedUrl, syndFeedInfo, connection);

        return syndFeedInfo.getSyndFeed();
    }

    @Override
    public SyndFeed retrieveFeed(String userAgent, URL url)
            throws IllegalArgumentException, IOException, FeedException, com.rometools.fetcher.FetcherException {
        return retrieveFeed(url);
    }

    private void refreshFeedInfo(URL feedUrl, com.rometools.fetcher.impl.SyndFeedInfo syndFeedInfo,
                                 URLConnection connection)
            throws IOException, FeedException {
        // need to always set the URL because this may have changed due to 3xx redirects
        syndFeedInfo.setUrl(connection.getURL());

        // the ID is a persistent value that should stay the same
        // even if the URL for the feed changes (eg, by 3xx redirects)
        syndFeedInfo.setId(feedUrl.toString());

        // This will be 0 if the server doesn't support or isn't setting the last modified header
        syndFeedInfo.setLastModified(connection.getLastModified());

        // get the contents
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
            SyndFeed syndFeed = this.readFeedFromStream(inputStream, connection);
            syndFeedInfo.setSyndFeed(syndFeed);
        }
        finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            catch (Exception e) {
                // ignore
            }
        }
    }

    private SyndFeed readFeedFromStream(InputStream inputStream, URLConnection connection)
            throws IOException, FeedException {
        BufferedInputStream bufferedInputStream;
        if ("gzip".equalsIgnoreCase(connection.getContentEncoding())) {
            // handle gzip encoded content
            bufferedInputStream = new BufferedInputStream(new GZIPInputStream(inputStream));
        }
        else {
            bufferedInputStream = new BufferedInputStream(inputStream);
        }
        XmlReader reader = null;
        if (connection.getHeaderField("Content-Type") != null) {
            reader = new XmlReader(bufferedInputStream, connection.getHeaderField("Content-Type"), true);
        }
        else {
            reader = new XmlReader(bufferedInputStream, true);
        }
        SyndFeedInput syndFeedInput = new SyndFeedInput();
        syndFeedInput.setPreserveWireFeed(isPreserveWireFeed());
        SyndFeed feed = syndFeedInput.build(reader);
        fireEvent(com.rometools.fetcher.FetcherEvent.EVENT_TYPE_FEED_RETRIEVED, connection, feed);

        return feed;
    }

}