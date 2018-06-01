package io.github.nobe0716.iwsu.hash;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import static io.github.nobe0716.iwsu.service.ShorteningMappingService.SHORTEN_URL_LEN;

@Slf4j
@Service
public class RandomStringUtilBasedShortenHashShortener implements ShortenHashGenerator {
	@Override
	public String digest(String original) {
		String shortenHash = RandomStringUtils.randomAlphanumeric(SHORTEN_URL_LEN);
		String shortenUrl = "http://localhost:8080/" + shortenHash;
		log.debug("original: {}, shortenUrl: {}", original, shortenUrl);
		return shortenHash;
	}
}
