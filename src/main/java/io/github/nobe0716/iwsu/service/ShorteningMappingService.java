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
	public static final int SHORTEN_URL_LEN = 8;
	private final ShorteningMappingRepository shorteningMappingRepository;
	private final ShortenHashGenerator shortenHashGenerator;

	public GenerationResult findOrSaveMapping(String url) {
		Optional<ShorteningMappingEntity> optional = Optional.ofNullable(shorteningMappingRepository.findByOriginal(url));
		ShorteningMappingEntity entity = optional.orElseGet(() ->
			shorteningMappingRepository.saveAndFlush(ShorteningMappingEntity.builder()
				.original(url)
				.shorten(shortenHashGenerator.digest(url))
				.build()));
		return GenerationResult.of(!optional.isPresent(), entity);
	}

	public Optional<ShorteningMappingEntity> findOne(long id) {
		return Optional.ofNullable(shorteningMappingRepository.findOne(id));
	}

	public Optional<ShorteningMappingEntity> findByShorten(String shorten) {
		return Optional.ofNullable(shorteningMappingRepository.findByShorten(shorten));
	}
}
