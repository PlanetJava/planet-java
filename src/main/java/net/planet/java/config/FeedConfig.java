package net.planet.java.config;

import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import net.planet.java.domain.FeedSource;
import net.planet.java.domain.RssFeedItem;
import net.planet.java.feed.FeedMessageSource;
import net.planet.java.feed.RssFeedMessageSelector;
import net.planet.java.repository.FeedSourceRepository;
import net.planet.java.repository.RssFeedItemRepository;
import net.planet.java.util.DateUtils;
import net.planet.java.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.support.tuple.Tuple2;
import org.springframework.integration.scheduling.PollerMetadata;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * @author Bazlur Rahman Rokon
 * @since 8/1/16.
 */

//Ref: https://github.com/spring-projects/spring-integration-java-dsl/wiki/spring-integration-java-dsl-reference#examples
@Configuration
public class FeedConfig {
	private static final Logger log = LoggerFactory.getLogger(FeedConfig.class);

	//TODO: read http://docs.spring.io/spring-integration/docs/latest-ga/reference/html/spring-integration-introduction.html
	//TODO : read https://github.com/spring-projects/spring-integration-java-dsl/wiki/spring-integration-java-dsl-reference#basics

	@Autowired
	private FeedSourceRepository feedSourceRepository;

	@Autowired
	private RssFeedItemRepository rssFeedItemRepository;

	@Value("${feed.source.poller.cron}")
	public String feedSourcePollerCron;

	@Bean
	public FeedMessageSource feedMessageSource() {

		return new FeedMessageSource(feedSourceRepository);
	}

	@Bean(name = PollerMetadata.DEFAULT_POLLER)
	public PollerMetadata poller() {

		return Pollers
			.cron(feedSourcePollerCron)
			.get();
	}

	@Bean
	public DirectChannel consumer() {
		DirectChannel directChannel = new DirectChannel();

		directChannel.subscribe(message -> {
			List<Tuple2<FeedSource, SyndFeed>> feeds = (List<Tuple2<FeedSource, SyndFeed>>) message.getPayload();

			List<RssFeedItem> feedList = feeds.stream()
				.map(feedTuple2 -> feedTuple2.getT2()
					.getEntries()
					.stream()
					.map(entry -> convertTo(feedTuple2.getT1(), feedTuple2.getT2().getFeedType(), entry)))
				.flatMap(rssFeedStream -> rssFeedStream)
				.collect(Collectors.toList());

			rssFeedItemRepository.save(feedList);
		});

		return directChannel;
	}

	//TODO move it to transformer
	private RssFeedItem convertTo(final FeedSource feedSource, final String feedType, final SyndEntry entry) {
		RssFeedItem rssFeedItem = new RssFeedItem();

		String author = entry.getAuthor();
		String description = entry.getDescription().getValue();
		String link = entry.getLink();
		Date publishedDate = entry.getPublishedDate();
		String categories = entry.getCategories().stream().map(SyndCategory::getName).collect(Collectors.joining(","));//TODO figure out later
		String title = entry.getTitle();

		rssFeedItem.setAuthor(author);
		rssFeedItem.setCategory(categories);
		rssFeedItem.setDescription(StringUtils.trim(description));
		rssFeedItem.setFeedSource(feedSource);
		rssFeedItem.setLink(link);
		rssFeedItem.setPublishedDate(DateUtils.convertTo(publishedDate));
		rssFeedItem.setTitle(StringUtils.trim(title));
		rssFeedItem.setType(feedType);
		rssFeedItem.setGuid(UUID.randomUUID().toString());

		return rssFeedItem;
	}


	@Bean
	public RssFeedMessageSelector messageSelector() {

		return new RssFeedMessageSelector();
	}

	//TODO :: Consideration
	// 1. We might need to have multiple splitter based on content
	// 2. Filter for filtering out undesired feeds
	// 3.
	@Bean
	public IntegrationFlow feedFlow() {

		return IntegrationFlows
			.from(feedMessageSource(), c -> {
				c.poller(poller());
			})
			.filter(source -> true) //TODO in case of filter
			.transform(source -> source) // TODO in case we need to transform
			.channel(this.consumer())
			.get();
	}
}
