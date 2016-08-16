package net.planet.java.service;

import lombok.RequiredArgsConstructor;
import net.planet.java.domain.RssFeedItem;
import net.planet.java.dto.RssFeedDto;
import net.planet.java.exceptions.ResourceDoesNotExistException;
import net.planet.java.repository.RssFeedRepository;
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

	private final RssFeedRepository rssFeedRepository;
	private final ModelMapper modelMapper;

	@Override
	public PageImpl<RssFeedDto> findAll(Pageable pageable) {
		long total = rssFeedRepository.count();
		List<RssFeedDto> rssFeedDtos = rssFeedRepository.findAll(pageable).map(this::convertToDto).getContent();

		return new PageImpl<>(rssFeedDtos, pageable, total);
	}

	@Override
	public RssFeedDto findOne(Long id) {

		return rssFeedRepository.findOneById(id)
			.map(this::convertToDto)
			.orElseThrow(ResourceDoesNotExistException::new);
	}

	private RssFeedDto convertToDto(RssFeedItem rssFeedItem) {

		return modelMapper.map(rssFeedItem, RssFeedDto.class);
	}
}
