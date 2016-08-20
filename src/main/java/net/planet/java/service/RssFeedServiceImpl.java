package net.planet.java.service;

import lombok.RequiredArgsConstructor;
import net.planet.java.domain.RssFeedItem;
import net.planet.java.dto.RssFeedItemDto;
import net.planet.java.exceptions.ResourceDoesNotExistException;
import net.planet.java.repository.RssFeedItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/6/16.
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class RssFeedServiceImpl implements RssFeedService {

	private final RssFeedItemRepository rssFeedItemRepository;
	private final ModelMapper modelMapper;

	@Override
	public PageImpl<RssFeedItemDto> findAll(Pageable pageable) {
		long total = rssFeedItemRepository.count();
		List<RssFeedItemDto> rssFeedItemDtos = rssFeedItemRepository.findAll(pageable).map(this::convertToDto).getContent();

		return new PageImpl<>(rssFeedItemDtos, pageable, total);
	}

	@Override
	public RssFeedItemDto findOne(Long id) {

		return rssFeedItemRepository.findOneById(id)
			.map(this::convertToDto)
			.orElseThrow(ResourceDoesNotExistException::new);
	}

	private RssFeedItemDto convertToDto(RssFeedItem rssFeedItem) {

		return modelMapper.map(rssFeedItem, RssFeedItemDto.class);
	}
}
