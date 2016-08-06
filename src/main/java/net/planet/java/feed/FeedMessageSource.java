package net.planet.java.feed;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import net.planet.java.domain.FeedSource;
import net.planet.java.repository.FeedSourceRepository;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.support.tuple.Tuple2;
import org.springframework.integration.dsl.support.tuple.Tuples;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/1/16.
 */
//TODO: for basic use case, this will work, however, we are going to have to improve this down the road
public class FeedMessageSource implements MessageSource, InitializingBean {
	private static final Logger log = LoggerFactory.getLogger(FeedMessageSource.class);

	private FeedSourceRepository feedSourceRepository;

	public FeedMessageSource(FeedSourceRepository feedSourceRepository) {
		this.feedSourceRepository = feedSourceRepository;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	@Override
	public Message receive() {
		List<Tuple2<FeedSource, SyndFeed>> feeds = obtainFeedItems();

		return MessageBuilder.withPayload(feeds)
			.setHeader("feedid", "javafeed")
			.build();
	}

	private List<Tuple2<FeedSource, SyndFeed>> obtainFeedItems() {
		try (CloseableHttpClient client = HttpClients.createMinimal()) {

			return feedSourceRepository.findAllByDeletedFalseAndExpiredFalse()
				.stream()
				.parallel()
				.map(feedSource -> {
					HttpUriRequest method = new HttpGet(feedSource.getUrl());

					try (CloseableHttpResponse response = client.execute(method);
					     InputStream stream = response.getEntity().getContent()) {
						SyndFeedInput input = new SyndFeedInput();
						SyndFeed syndFeed = input.build(new XmlReader(stream));

						feedSource.setLastVisit(LocalDateTime.now());
						feedSourceRepository.save(feedSource);

						return Tuples.of(feedSource, syndFeed);
					} catch (IOException | FeedException e) {
						log.error("could not fetch feed from url: " + feedSource.getUrl());
						throw new RuntimeException(e);
					}
				}).collect(Collectors.toList());
		} catch (IOException e) {
			log.error("could not fetch feed items ");
		}

		return Collections.emptyList();
	}
}
