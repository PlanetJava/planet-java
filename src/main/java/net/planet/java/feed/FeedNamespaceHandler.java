package net.planet.java.feed;

import org.springframework.integration.config.xml.AbstractIntegrationNamespaceHandler;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/16/16.
 */
public class FeedNamespaceHandler extends AbstractIntegrationNamespaceHandler {
    @Override
    public void init() {
        registerBeanDefinitionParser("inbound-channel-adapter", new FeedInboundChannelAdapterParser());
    }
}
