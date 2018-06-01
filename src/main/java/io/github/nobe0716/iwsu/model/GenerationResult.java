package io.github.nobe0716.iwsu.model;

import io.github.nobe0716.iwsu.entity.ShorteningMappingEntity;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class GenerationResult {
	private final boolean isNew;
	private final ShorteningMappingEntity entity;

	public static GenerationResult of(boolean isNew, ShorteningMappingEntity entity) {
		return GenerationResult.builder()
			.isNew(isNew)
			.entity(entity)
			.build();
	}
}
