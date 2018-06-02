package io.github.nobe0716.iwsu.service;

import io.github.nobe0716.iwsu.entity.ShorteningMappingEntity;
import io.github.nobe0716.iwsu.hash.ShortenHashGenerator;
import io.github.nobe0716.iwsu.model.GenerationResult;
import io.github.nobe0716.iwsu.repository.ShorteningMappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShorteningMappingService {
	private final ShorteningMappingRepository shorteningMappingRepository;
	private final ShortenHashGenerator shortenHashGenerator;

	public GenerationResult findOrSaveMapping(String url) {
		Optional<ShorteningMappingEntity> optional = Optional.ofNullable(shorteningMappingRepository.findByOriginalUrl(url));
		ShorteningMappingEntity entity = optional.orElseGet(() ->
			shorteningMappingRepository.saveAndFlush(ShorteningMappingEntity.builder()
				.originalUrl(url)
				.shortenHash(shortenHashGenerator.digest(url))
				.build()));
		return GenerationResult.of(!optional.isPresent(), entity);
	}

	public Optional<ShorteningMappingEntity> findOne(long id) {
		return Optional.ofNullable(shorteningMappingRepository.findOne(id));
	}

	public Optional<ShorteningMappingEntity> findByShorten(String shortenHash) {
		return Optional.ofNullable(shorteningMappingRepository.findByShortenHash(shortenHash));
	}
}
