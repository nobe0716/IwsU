package io.github.nobe0716.iwsu.model.res;

import io.github.nobe0716.iwsu.model.GenerationResult;
import lombok.Data;

@Data(staticConstructor = "of")
public class GenerateShortenRto {
	private final boolean isNew;
	private final ShortenMappingRto shortenMapping;

	public static GenerateShortenRto of(GenerationResult result) {
		return GenerateShortenRto.of(result.isNew(), ShortenMappingRto.of(result.getEntity()));
	}
}
