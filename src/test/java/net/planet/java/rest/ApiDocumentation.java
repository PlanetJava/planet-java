package net.planet.java.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.planet.java.PlanetJavaApplication;
import net.planet.java.domain.FeedSource;
import net.planet.java.repository.FeedSourceRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.RequestDispatcher;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PlanetJavaApplication.class)
@WebAppConfiguration
public class ApiDocumentation {
    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

    private RestDocumentationResultHandler documentationHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FeedSourceRepository feedSourceRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.documentationHandler = document("{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.documentationHandler)
                .build();
    }

    @Test
    public void headersExample() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/"))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        responseHeaders(
                                headerWithName("Content-Type").description("The Content-Type of the payload, e.g. `application/hal+json`"))));
    }

    @Test
    public void errorExample() throws Exception {
        this.mockMvc
                .perform(get("/error")
                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
                        .requestAttr(RequestDispatcher.ERROR_REQUEST_URI, "/feed-source")
                        .requestAttr(RequestDispatcher.ERROR_MESSAGE, "The tag 'http://localhost:8080/feed-source/123' does not exist"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("error", is("Bad Request")))
                .andExpect(jsonPath("timestamp", is(notNullValue())))
                .andExpect(jsonPath("status", is(400)))
                .andExpect(jsonPath("path", is(notNullValue())))
                .andDo(this.documentationHandler.document(
                        responseFields(
                                fieldWithPath("error").description("The HTTP error that occurred, e.g. `Bad Request`"),
                                fieldWithPath("message").description("A description of the cause of the error"),
                                fieldWithPath("path").description("The path to which the request was made"),
                                fieldWithPath("status").description("The HTTP status code, e.g. `400`"),
                                fieldWithPath("timestamp").description("The time, in milliseconds, at which the error occurred"))));
    }

    @Test
    public void indexExample() throws Exception {
        this.mockMvc.perform(get("/api/v1/"))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        links(
                                linkWithRel("feedSources").description("The <<resources-feed-sources,FeedSource resource>>")),
                        responseFields(
                                fieldWithPath("_links").description("<<resources-index-links,Links>> to other resources"))));
    }

    @Test
    public void feedSourceListExample() throws Exception {
        this.feedSourceRepository.deleteAll();

        createFeedSource("http://www.bazlur.com/feeds/posts/default?alt=rss", "Bazlur's Steams of Thoughts", "This blog is meant to be a vehicle for airing my interests, feelings and opinion");
        createFeedSource("http://www.bazlur.com/feeds/posts/default?alt=rss", "Bazlur's Steams of Thoughts", "This blog is meant to be a vehicle for airing my interests, feelings and opinion");
        createFeedSource("http://www.bazlur.com/feeds/posts/default?alt=rss", "Bazlur's Steams of Thoughts", "This blog is meant to be a vehicle for airing my interests, feelings and opinion");

        this.mockMvc
                .perform(get("/api/v1/feed-source"))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        responseFields(
                                fieldWithPath("_embedded.feedSourceDtoList").description("An array of <<resources-feed-source, FeedSource resources>>"))));
    }


    private void createFeedSource(String url, String name, String description) {
        FeedSource feedSource = new FeedSource();
        feedSource.setUrl(url);
        feedSource.setDescription(description);
        feedSource.setSiteName(name);
        feedSource.setLastVisit(LocalDateTime.now());

        this.feedSourceRepository.save(feedSource);
    }


}
