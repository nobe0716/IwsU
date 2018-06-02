package io.github.nobe0716.iwsu.util;

import io.github.nobe0716.iwsu.entity.ShorteningMappingEntity;
import io.github.nobe0716.iwsu.type.IwsuConstant;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class DummyParamGenerator {
	private static final String SHORTEN_URL = "http://localhost/" + RandomStringUtils.randomAlphanumeric(IwsuConstant.SHORTEN_URL_LEN);

	public static ShorteningMappingEntity getShorteningMappingEntity(String url) {
		return getShorteningMappingEntity(url, SHORTEN_URL);
	}

	public static ShorteningMappingEntity getShorteningMappingEntity(String originalUrl, String shortenHash) {
		return ShorteningMappingEntity.builder()
			.id(RandomUtils.nextLong())
			.originalUrl(originalUrl)
			.shortenHash(shortenHash)
			.build();
	}
}
