package io.github.nobe0716.iwsu.service;

import io.github.nobe0716.iwsu.entity.ShorteningMappingEntity;
import io.github.nobe0716.iwsu.hash.HashCodeBaseShortener;
import io.github.nobe0716.iwsu.model.GenerationResult;
import io.github.nobe0716.iwsu.repository.ShorteningMappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShorteningMappingService {
	private final ShorteningMappingRepository shorteningMappingRepository;
	private final HashCodeBaseShortener shortenHashGenerator;

	public GenerationResult findOrSaveMapping(String url) {
		Optional<ShorteningMappingEntity> optional = Optional.ofNullable(shorteningMappingRepository.findByOriginalUrl(url));
		ShorteningMappingEntity entity = optional.orElseGet(() ->
			shorteningMappingRepository.saveAndFlush(ShorteningMappingEntity.builder()
				.originalUrl(url)
				.shortenHash(findShortenHash(url))
				.build()));
		return GenerationResult.of(!optional.isPresent(), entity);
	}

	/**
	 * Conflict가 나지 않는 Hash를 찾아서 반환
	 *
	 * @param url 원본 url
	 * @return Hash
	 */
	private String findShortenHash(String url) {
		String digest = shortenHashGenerator.digest(url);
		while (findByShorten(digest).isPresent()) {
			String appendedUrl = RandomStringUtils.randomAlphanumeric(5);
			digest = shortenHashGenerator.digest(appendedUrl);
		}
		return digest;
	}

	public Optional<ShorteningMappingEntity> findOne(long id) {
		return Optional.ofNullable(shorteningMappingRepository.findOne(id));
	}

	public Optional<ShorteningMappingEntity> findByShorten(String shortenHash) {
		return Optional.ofNullable(shorteningMappingRepository.findByShortenHash(shortenHash));
	}
}
