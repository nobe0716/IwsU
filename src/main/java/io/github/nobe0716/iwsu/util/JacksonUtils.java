package io.github.nobe0716.iwsu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class JacksonUtils {
	private static final ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static Optional<String> toJson(Object o) {
		try {
			return Optional.of(objectMapper.writeValueAsString(o));
		} catch (JsonProcessingException e) {
			log.error("ERROR JacksonUtils.toJson({})", o, e);
			return Optional.empty();
		}
	}
}
