package io.github.nobe0716.iwsu.model.res;

import io.github.nobe0716.iwsu.entity.ShorteningMappingEntity;
import lombok.Data;

@Data(staticConstructor = "of")
public class ShortenMappingRto {
	private final String shorten;
	private final String original;

	public static ShortenMappingRto of(ShorteningMappingEntity entity) {
		return ShortenMappingRto.of(entity.getShorten(), entity.getOriginal());
	}
}
