package io.github.nobe0716.iwsu.model.res;

import io.github.nobe0716.iwsu.entity.ShorteningMappingEntity;
import lombok.Data;

@Data(staticConstructor = "of")
public class ShorteningMappingRto {
	private final String originalUrl;
	private final String shortenHash;

	public static ShorteningMappingRto of(ShorteningMappingEntity entity, String baseUrl) {
		return ShorteningMappingRto.of(entity.getOriginalUrl(), baseUrl + entity.getShortenHash());
	}
}
