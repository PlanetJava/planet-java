package net.planet.java.feed;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/1/16.
 */
public class FeedReader implements MessageSource, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(FeedReader.class);

    private List<String> urls;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public Message receive() {
        List<SyndFeed> feeds = obtainFeedItems();

        return MessageBuilder.withPayload(feeds)
                .setHeader("feedid", "javafeed")
                .build();
    }

    private List<SyndFeed> obtainFeedItems() {
        try (CloseableHttpClient client = HttpClients.createMinimal()) {

            return urls
                    .stream()
                    .map(url -> {
                        HttpUriRequest method = new HttpGet(url);

                        try (CloseableHttpResponse response = client.execute(method);
                             InputStream stream = response.getEntity().getContent()) {
                            SyndFeedInput input = new SyndFeedInput();

                            return input.build(new XmlReader(stream));
                        } catch (IOException | FeedException e) {
                            log.error("could not fetch feed from url: " + url);
                            throw new RuntimeException(e);
                        }
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("could not fetch feed items ");
        }

        return Collections.emptyList();
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
