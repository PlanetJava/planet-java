package net.planet.java.service;

import lombok.RequiredArgsConstructor;
import net.planet.java.domain.FeedSource;
import net.planet.java.dto.FeedSourceDto;
import net.planet.java.exceptions.ResourceDoesNotExistException;
import net.planet.java.repository.FeedSourceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class FeedSourceServiceImpl implements FeedSourceService {
    private final FeedSourceRepository feedSourceRepository;
    private final ModelMapper modelMapper;

    public List<FeedSourceDto> findAll() {

        return feedSourceRepository.findAllByDeletedFalseAndExpiredFalse()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public FeedSourceDto findOne(Long id) {

        return feedSourceRepository
                .findOneById(id)
                .map(this::convertToDto)
                .orElseThrow(ResourceDoesNotExistException::new);
    }

    @Override
    public FeedSourceDto create(FeedSourceDto feedSourceDto) {
        FeedSource feedSource = convertToEntity(feedSourceDto);
        FeedSource savedFeedSource = feedSourceRepository.save(feedSource);

        return convertToDto(savedFeedSource);
    }

    @Override
    public FeedSourceDto update(Long id, FeedSourceDto feedSourceDto) {
        //TODO verify id exist first

        return feedSourceRepository
                .findOneById(id)
                .map(feedSource -> {
                    feedSource.setSiteName(feedSourceDto.getSiteName());
                    feedSource.setUrl(feedSourceDto.getUrl());
                    feedSource.setDescription(feedSourceDto.getDescription());
                    FeedSource savedFeedSource = feedSourceRepository.save(feedSource);

                    return convertToDto(savedFeedSource);
                }).orElseThrow(ResourceDoesNotExistException::new);
    }

    @Override
    public void deleteById(Long id) {
        feedSourceRepository
                .findOneById(id)
                .map(feedSource -> {
                    feedSource.setDeleted(true); //set deleted

                    feedSourceRepository.save(feedSource);
                    return feedSource;
                })
                .orElseThrow(ResourceDoesNotExistException::new);
    }

    private FeedSourceDto convertToDto(FeedSource feedSource) {

        return modelMapper.map(feedSource, FeedSourceDto.class);
    }

    private FeedSource convertToEntity(FeedSourceDto feedSourceDto) {
        FeedSource feedSource = modelMapper.map(feedSourceDto, FeedSource.class);

        if (feedSource.getId() != null) {
            FeedSource oldFeedSource = feedSourceRepository.findOne(feedSourceDto.getId());
            feedSource.setSiteName(oldFeedSource.getSiteName());
            feedSource.setUrl(oldFeedSource.getUrl());
            feedSource.setDescription(oldFeedSource.getDescription());
            feedSource.setLastVisit(oldFeedSource.getLastVisit());
        }

        return feedSource;
    }
}
