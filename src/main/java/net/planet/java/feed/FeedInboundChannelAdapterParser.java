package net.planet.java.feed;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractPollingInboundChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/16/16.
 */
public class FeedInboundChannelAdapterParser extends AbstractPollingInboundChannelAdapterParser {
    @Override
    protected BeanMetadataElement parseSource(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder sourceBuilder = BeanDefinitionBuilder.genericBeanDefinition(FeedEntryMessageSource.class);
        sourceBuilder.addConstructorArgValue(element.getAttribute("url"));
        sourceBuilder.addConstructorArgValue(element.getAttribute(ID_ATTRIBUTE));
        String feedFetcherRef = element.getAttribute("feed-fetcher");
        if (StringUtils.hasText(feedFetcherRef)) {
            sourceBuilder.addConstructorArgReference(feedFetcherRef);
        }
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(sourceBuilder, element, "metadata-store");

        return sourceBuilder.getBeanDefinition();
    }
}
