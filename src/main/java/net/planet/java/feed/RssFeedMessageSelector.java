package net.planet.java.feed;

import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/6/16.
 */
@Component
public class RssFeedMessageSelector implements MessageSelector {

	@Override
	public boolean accept(Message<?> message) {
		Object payload = message.getPayload();

		//TODO filter
		return false;
	}
}
