package io.github.nobe0716.iwsu.service;

import io.github.nobe0716.iwsu.entity.ShorteningMappingEntity;
import io.github.nobe0716.iwsu.model.GenerationResult;
import io.github.nobe0716.iwsu.repository.ShorteningMappingRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShorteningMappingService {
	private static final int SHORTEN_URL_LEN = 8;
	private final ShorteningMappingRepository shorteningMappingRepository;

	public GenerationResult findOrSaveMapping(String url) {
		ShorteningMappingEntity fromDB = shorteningMappingRepository.findByOriginal(url);
		if (fromDB != null) {
			return GenerationResult.of(false, fromDB);
		}
		ShorteningMappingEntity newEntity = ShorteningMappingEntity.builder()
				.original(url)
				.shorten(RandomStringUtils.randomAlphanumeric(SHORTEN_URL_LEN))
				.build();
		return GenerationResult.of(true, shorteningMappingRepository.save(newEntity));
	}

	public ShorteningMappingEntity findOne(long id) {
		return shorteningMappingRepository.findOne(id);
	}
}
