package net.planet.java.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.planet.java.PlanetJavaApplication;
import net.planet.java.constants.ApiLinks;
import net.planet.java.domain.FeedSource;
import net.planet.java.dto.FeedSourceDto;
import net.planet.java.repository.FeedSourceRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.RequestDispatcher;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

	@After
	public void after(){
		this.feedSourceRepository.deleteAll();
	}

	@Test
	public void headersExample() throws Exception {
		this.mockMvc
			.perform(get(ApiLinks.INDEX))
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
				.requestAttr(RequestDispatcher.ERROR_REQUEST_URI, "/error")
				.requestAttr(RequestDispatcher.ERROR_MESSAGE, "The tag 'http://localhost:8080/error' does not exist"))
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
		this.mockMvc.perform(get(ApiLinks.INDEX))
			.andExpect(status().isOk())
			.andDo(this.documentationHandler.document(
				links(
					linkWithRel("feedSources").description("The <<resources-feed-sources,FeedSource resource>>"),
					linkWithRel("rssFeedItems").description("The <<resources-rss-feeds,RssFeedItem resource>>")),
				responseFields(
					fieldWithPath("_links").description("<<resources-index-links,Links>> to other resources"))
			));
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
					fieldWithPath("_embedded.feedSources").description("An array of <<resources-feed-source, FeedSource resources>>"))));

		this.feedSourceRepository.deleteAll();
	}

	@Test
	public void feedSourceCreateExample() throws Exception {
		FeedSourceInput feedSourceInput = new FeedSourceInput();
		feedSourceInput.setUrl("http://www.bazlur.com/feeds/posts/default?alt=rss");
		feedSourceInput.setSiteName("Bazlur's WebSite");
		feedSourceInput.setDescription("--- ---- ----");

		ConstrainedFields fields = new ConstrainedFields(FeedSourceInput.class);

		this.mockMvc.perform(post(ApiLinks.API_FEED_SOURCE)
			.contentType(MediaTypes.HAL_JSON)
			.content(this.objectMapper.writeValueAsString(feedSourceInput)))
			.andExpect(status().isCreated())
			.andDo(this.documentationHandler.document(
				requestFields(
					fields.withPath("url").description("Url of the feed source"),
					fields.withPath("siteName").description("Feed source site name"),
					fields.withPath("description").description("Description of the site"))));
	}


	@Test
	public void feedSourceCreateValidationExample() throws Exception {
		FeedSourceInput feedSourceInput = new FeedSourceInput();
		feedSourceInput.setSiteName("Bazlur's WebSite");
		feedSourceInput.setDescription("--- ---- ----");

		ConstrainedFields fields = new ConstrainedFields(FeedSourceInput.class);

		this.mockMvc.perform(post(ApiLinks.API_FEED_SOURCE)
			.contentType(MediaTypes.HAL_JSON)
			.content(this.objectMapper.writeValueAsString(feedSourceInput)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.fieldErrors", hasSize(1)))
			.andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("url")))
			.andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder(
				"Url must not be empty."
			)));

	}


	@Test
	public void feedSourceUpdateExample() throws Exception {
		FeedSourceDto feedSourceDto = new FeedSourceDto();
		feedSourceDto.setUrl("http://www.bazlur.com/feeds/posts/default?alt=rss");
		feedSourceDto.setSiteName("Bazlur's Steams of Thoughts");
		feedSourceDto.setDescription("This blog is meant to be a vehicle for airing my interests, feelings and opinion");

		String feedSourceLocation = this.mockMvc.perform(post(ApiLinks.API_FEED_SOURCE)
			.contentType(MediaTypes.HAL_JSON)
			.content(this.objectMapper.writeValueAsString(feedSourceDto)))
			.andExpect(status().isCreated())
			.andReturn().getResponse().getHeader("Location");

		this.mockMvc
			.perform(get(feedSourceLocation))
			.andExpect(status().isOk())
			.andExpect(jsonPath("url", is(feedSourceDto.getUrl())))
			.andExpect(jsonPath("siteName", is(feedSourceDto.getSiteName())))
			.andExpect(jsonPath("description", is(feedSourceDto.getDescription())))
			.andExpect(jsonPath("_links.self.href", is(notNullValue())));

		FeedSourceInput feedSourceInput = new FeedSourceInput();
		feedSourceInput.setUrl("http://www.bazlur.com/feeds/posts/default?alt=rss");
		feedSourceInput.setSiteName("Bazlur's WebSite");
		feedSourceInput.setDescription("--- ---- ----");

		ConstrainedFields fields = new ConstrainedFields(FeedSourceInput.class);

		this.mockMvc.perform(put(feedSourceLocation)
			.contentType(MediaTypes.HAL_JSON)
			.content(this.objectMapper.writeValueAsString(feedSourceInput)))
			.andExpect(status().isNoContent())
			.andDo(this.documentationHandler.document(
				requestFields(
					fields.withPath("url")
						.type(JsonFieldType.STRING)
						.description("Url of the feed source"),
					fields.withPath("siteName")
						.type(JsonFieldType.STRING)
						.description("Feed source site name"),
					fields.withPath("description")
						.type(JsonFieldType.STRING)
						.description("Description of the site"))));
	}

	@Test
	public void feedSourceGetExample() throws Exception {
		FeedSourceDto feedSourceDto = new FeedSourceDto();
		feedSourceDto.setUrl("http://www.bazlur.com/feeds/posts/default?alt=rss");
		feedSourceDto.setSiteName("Bazlur's Steams of Thoughts");
		feedSourceDto.setDescription("This blog is meant to be a vehicle for airing my interests, feelings and opinion");

		String feedSourceLocation = this.mockMvc.perform(post(ApiLinks.API_FEED_SOURCE)
			.contentType(MediaTypes.HAL_JSON)
			.content(this.objectMapper.writeValueAsString(feedSourceDto)))
			.andExpect(status().isCreated())
			.andReturn().getResponse().getHeader("Location");

		System.err.println(" feedSourceLocation" + feedSourceLocation);
		this.mockMvc
			.perform(get(feedSourceLocation))
			.andExpect(status().isOk())
			.andExpect(jsonPath("url", is(feedSourceDto.getUrl())))
			.andExpect(jsonPath("siteName", is(feedSourceDto.getSiteName())))
			.andExpect(jsonPath("description", is(feedSourceDto.getDescription())))
			.andExpect(jsonPath("_links.self.href", is(notNullValue())))
			.andDo(this.documentationHandler.document(
				links(linkWithRel("self").description("This <<resources-feedSource>>")),
				responseFields(
					fieldWithPath("id").description("The id of feedSource"),
					fieldWithPath("url").description("The Url of feedSource"),
					fieldWithPath("siteName").description("The Site name of feedSource"),
					fieldWithPath("description").description("The description of feedSource"),
					fieldWithPath("lastVisit").description("The date time when the feedSource was last time visited"),
					fieldWithPath("_links.self.href").description("The link of self resource")
				)
			));
	}

	@Test
	public void feedSourceNotFoundExample() throws Exception {
		this.mockMvc
			.perform(get(ApiLinks.API_FEED_SOURCE + "/" + Long.MAX_VALUE))
			.andExpect(status().isNotFound());

			//.andExpect(jsonPath("timestamp", is(notNullValue())))
			//.andExpect(jsonPath("status", is(404)))
			//.andExpect(jsonPath("path", is(notNullValue())))
//			.andDo(this.documentationHandler.document(
//				responseFields(
//					fieldWithPath("error").description("The HTTP error that occurred, e.g. `Not Found`"),
//					fieldWithPath("message").description("A description of the cause of the error"),
//					fieldWithPath("path").description("The path to which the request was made"),
//					fieldWithPath("status").description("The HTTP status code, e.g. `404`"),
//					fieldWithPath("timestamp").description("The time, in milliseconds, at which the error occurred"))));

	}

	@Test
	public void deleteExample() throws Exception {
		FeedSourceDto feedSourceDto = new FeedSourceDto();
		feedSourceDto.setUrl("http://www.bazlur.com/feeds/posts/default?alt=rss");
		feedSourceDto.setSiteName("Bazlur's Steams of Thoughts");
		feedSourceDto.setDescription("This blog is meant to be a vehicle for airing my interests, feelings and opinion");

		String feedSourceLocation = this.mockMvc.perform(post(ApiLinks.API_FEED_SOURCE)
			.contentType(MediaTypes.HAL_JSON)
			.content(this.objectMapper.writeValueAsString(feedSourceDto)))
			.andExpect(status().isCreated())
			.andReturn().getResponse().getHeader("Location");

		this.mockMvc
			.perform(delete(feedSourceLocation))
			.andExpect(status().isOk());
	}

	private void createFeedSource(String url, String name, String description) {
		FeedSource feedSource = new FeedSource();
		feedSource.setUrl(url);
		feedSource.setDescription(description);
		feedSource.setSiteName(name);
		feedSource.setLastVisit(LocalDateTime.now());

		this.feedSourceRepository.save(feedSource);
	}

	private static class ConstrainedFields {

		private final ConstraintDescriptions constraintDescriptions;

		ConstrainedFields(Class<?> input) {
			this.constraintDescriptions = new ConstraintDescriptions(input);
		}

		private FieldDescriptor withPath(String path) {

			return fieldWithPath(path).attributes(key("constraints").value(StringUtils
				.collectionToDelimitedString(this.constraintDescriptions
					.descriptionsForProperty(path), ". ")));
		}
	}
}
