package net.planet.java.service;

import net.planet.java.domain.FeedSource;
import net.planet.java.dto.FeedSourceDto;
import net.planet.java.exceptions.ResourceDoesNotExistException;
import net.planet.java.repository.FeedSourceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/4/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedSourceServiceImplTest {

    @MockBean
    private FeedSourceRepository feedSourceRepository;

    @Autowired
    private FeedSourceService feedSourceService;

    @Before
    public void setupMock() {
        feedSourceService = new FeedSourceServiceImpl(feedSourceRepository, new ModelMapper());
    }

    @Test
    public void findAll_GivenEmptyData_ShouldReturnEmptyList() throws Exception {
        given(this.feedSourceRepository.findAllByDeletedFalseAndExpiredFalse()).willReturn(Collections.emptyList());

        List<FeedSourceDto> feedSources = this.feedSourceService.findAll();
        assertThat(feedSources.size(), is(0));
    }

    @Test
    public void findAll_GivenTwoData_ShouldReturnListOfTwo() throws Exception {
        List<FeedSource> feedSourceList = new ArrayList<FeedSource>() {{
            add(new FeedSource());
            add(new FeedSource());
        }};
        given(this.feedSourceRepository.findAllByDeletedFalseAndExpiredFalse()).willReturn(feedSourceList);

        List<FeedSourceDto> feedSources = this.feedSourceService.findAll();
        assertThat(feedSources.size(), is(2));
    }

    @Test(expected = ResourceDoesNotExistException.class)
    public void findOne_GivenEmptyDataSet_ShouldThrowException() throws Exception {
        long id = 4;
        given(this.feedSourceRepository.findOneById(id)).willReturn(Optional.empty());

        this.feedSourceService.findOne(id);
    }

    //TODO figure out create/update/and delete test
}