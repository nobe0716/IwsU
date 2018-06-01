package io.github.nobe0716.iwsu.model;

import io.github.nobe0716.iwsu.entity.ShorteningMappingEntity;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GenerationResult {
	private final boolean isNew;
	private final ShorteningMappingEntity entity;
	private long id; // id of entity

	public static GenerationResult of(boolean isNew, ShorteningMappingEntity entity) {
		return GenerationResult.builder()
				.id(entity.getId())
				.isNew(isNew)
				.build();
	}
}
