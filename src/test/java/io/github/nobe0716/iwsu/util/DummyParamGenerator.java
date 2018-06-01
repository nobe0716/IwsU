package io.github.nobe0716.iwsu.util;

import io.github.nobe0716.iwsu.entity.ShorteningMappingEntity;
import io.github.nobe0716.iwsu.service.ShorteningMappingService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class DummyParamGenerator {
	private static final String SHORTEN_URL = "http://localhost/" + RandomStringUtils.randomAlphanumeric(ShorteningMappingService.SHORTEN_URL_LEN);

	public static ShorteningMappingEntity getShorteningMappingEntity(String url) {
		return ShorteningMappingEntity.builder()
			.id(RandomUtils.nextLong())
			.original(url)
			.shorten(SHORTEN_URL)
			.build();
	}
}
