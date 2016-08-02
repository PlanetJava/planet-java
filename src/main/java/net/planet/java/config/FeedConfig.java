package net.planet.java.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/1/16.
 */

//Ref: https://github.com/spring-projects/spring-integration-java-dsl/wiki/spring-integration-java-dsl-reference#examples
@Configuration
public class FeedConfig {

    //TODO: read http://docs.spring.io/spring-integration/docs/latest-ga/reference/html/spring-integration-introduction.html


//    @Bean
//    public MessageSource<Object> jdbcMessageSource() {
//        return new JdbcPollingChannelAdapter(this.dataSource, "SELECT * FROM foo");
//    }
//
//    @Bean
//    public DirectChannel inputChannel() {
//
//        return new DirectChannel();
//    }
//
//    @Bean
//    public IntegrationFlow myFlow() {
//        return IntegrationFlows.from(this.integerMessageSource(), c ->
//                c.poller(Pollers.fixedRate(100)))
//                .channel(this.inputChannel())
//                .filter((Integer p) -> p > 0)
//                .transform(Object::toString)
//                .channel(MessageChannels.queue())
//                .get();
//    }

}
